package hu.bme.aut.android.languagelearner.data.repository

import hu.bme.aut.android.languagelearner.data.local.*
import hu.bme.aut.android.languagelearner.data.mapper.toDatabase
import hu.bme.aut.android.languagelearner.data.mapper.toDomain
import hu.bme.aut.android.languagelearner.data.network.WordApi
import hu.bme.aut.android.languagelearner.data.network.dto.WordPairDTO
import hu.bme.aut.android.languagelearner.data.network.dto.WordSetDTO
import hu.bme.aut.android.languagelearner.domain.model.WordPair
import hu.bme.aut.android.languagelearner.domain.model.WordSet
import hu.bme.aut.android.languagelearner.domain.model.WordTag
import hu.bme.aut.android.languagelearner.domain.repository.WordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WordRepositoryImpl @Inject constructor(
    private val wordSetDao: WordSetDao,
    private val wordDao: WordDao,
    private val tagDao: TagDao,
    private val wordApi: WordApi
): WordRepository {

    override suspend fun getWordSets(searchQuery: String, searchTags: List<Int>): Flow<List<WordSet>> {
        return flow {
            val list = wordSetDao.getWordSets(searchQuery)
            if (searchTags.isEmpty()){
                emit(list.map(PopulatedWordSet::toDomain))
            }else{
                val map = list.filter { wordSet ->
                    val result = wordSet.tags.map { tag -> tag.id }.containsAll(searchTags)
                    result
                }.map(PopulatedWordSet::toDomain)
                emit(map)
            }
        }
    }

    override suspend fun sync() {
        val courses = wordApi.getAllCourses()

        wordSetDao.upsertWordSets(courses.map(WordSetDTO::toDatabase))

        courses.forEach { course ->
            val tags = course.metadata
            val removeExistingTags = tags.filter { tag -> tagDao.getTagIdByTag(tag) == null }
            tagDao.upsertWordTags(removeExistingTags.map {tag -> WordTagEntity(0, tag) })
            wordSetDao.insertWordSetWordTagCrossRefs(
                tags.map { tag ->
                    WordSetWordTagCrossRef(
                        WordSetId = course.id,
                        WordTag = tagDao.getTagIdByTag(tag)!!
                    )
                }
            )
        }

        courses.forEach{ course ->
            val words = wordApi.getAllWordsByCourseId(course.id)

            wordDao.upsertWordPairs(words.map(WordPairDTO::toDatabase))
            wordSetDao.insertWordSetWordPairCrossRefs(
                words.map { word ->
                    WordSetWordPairCrossRef(
                        WordSetId = course.id,
                        WordPairId = word.id
                    )
                }
            )
        }
    }

    override suspend fun sendScore(courseId: Int, score: Int) {
        wordApi.sendScore(courseId, score)
    }

    override suspend fun clearAll() {
        wordSetDao.clearWordSetWordTagCrossRefs()
        wordSetDao.clearWordSetWordPairCrossRefs()
        wordSetDao.clearWordSets()
        wordDao.clearWords()
        tagDao.clearTags()
    }

    override suspend fun getWordsBySetId(id: Int): List<WordPair> =
        wordDao.getWordPairsBySetId(id).map (WordPairEntity::toDomain)

    override fun getTags(): Flow<List<WordTag>> =
        tagDao.getTags().map { it.map(WordTagEntity::toDomain)}

    override suspend fun wordMemorizedChanged(wordPairId: Int, memorized: Boolean){
        wordDao.updateWordMemorized(wordPairId, memorized)
        wordApi.updateWordMemorized(wordPairId, memorized)
    }
}