package hu.bme.aut.android.languagelearner.data.repository

import androidx.sqlite.db.SimpleSQLiteQuery
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
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WordRepositoryImpl @Inject constructor(
    private val wordSetDao: WordSetDao,
    private val wordDao: WordDao,
    private val tagDao: TagDao,
    private val wordApi: WordApi
): WordRepository {

    override fun getWordSets(searchQuery: String, searchTags: Set<Int>): Flow<List<WordSet>> {
        val query = if (searchTags.isEmpty()){
            SimpleSQLiteQuery("select * from word_sets where LOWER(title) Like '%' || LOWER(?) || '%' or LOWER(description) Like '%' || LOWER(?) || '%'", arrayOf(searchQuery, searchQuery))
        }else{
            //TODO fix
            //Log.d("valami", searchTags.toString())
            SimpleSQLiteQuery("select * from word_sets where id in (select word_set_id from word_sets_word_tags where word_tag_id in (?)) and (LOWER(title) Like '%' || LOWER(?) || '%' or LOWER(description) Like '%' || LOWER(?) || '%')", arrayOf(
                setOf(1), searchQuery,searchQuery))
        }
        val result = wordSetDao.getWordSets(query).map {
            //Log.d("valami", it.toString())
            it.map(PopulatedWordSet::toDomain) }
        return result
    }

    override suspend fun sync() {
        val courses = wordApi.getAllCourses()

        wordSetDao.upsertWordSets(courses.map(WordSetDTO::toDatabase))

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

        /*wordSetDao.insertWordSets(wordSets = wordSets.map { WordSetEntity(it.id, it.title, it.description) })
        wordDao.insertWordPairs(wordPairs = wordSets.map { wordSet -> wordSet.words.map { WordPairEntity(it.id, it.first, it.second, it.memorized) } }.flatten() )
        tagDao.insertWordTags(wordTags = wordSets.map { wordSet -> wordSet.tags.map { WordSetTagEntity(id = 0, tag = it.tag) } }.flatten())
        wordSetDao.insertWordSetWordPairCrossRefs(wordSetWordPairCrossRefs = wordSets.map { wordSet -> wordSet.words.map { word -> WordSetWordPairCrossRef(WordSetId = wordSet.id, WordPairId = word.id) } }.flatten())
        wordSetDao.insertWordSetWordTagCrossRefs(wordSetWordTagCrossRefs = wordSets.map { wordSet -> wordSet.tags.map { tag -> WordSetWordTagCrossRef(WordSetId = wordSet.id, WordTag = tagDao.getTagIdByTag(tag = tag.tag)) } }.flatten())*/
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