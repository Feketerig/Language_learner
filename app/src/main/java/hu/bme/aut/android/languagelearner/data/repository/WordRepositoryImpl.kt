package hu.bme.aut.android.languagelearner.data.repository

import hu.bme.aut.android.languagelearner.data.local.*
import hu.bme.aut.android.languagelearner.data.mapper.toDomain
import hu.bme.aut.android.languagelearner.data.network.WordApi
import hu.bme.aut.android.languagelearner.domain.model.WordSet
import hu.bme.aut.android.languagelearner.domain.repository.WordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WordRepositoryImpl @Inject constructor(
    private val wordDao: WordDao,
    private val wordApi: WordApi
): WordRepository {

    override fun getWordSets(
        searchTags: Set<String>
    ): Flow<List<WordSet>> =
        wordDao.getWordSets(searchTags = searchTags).map { it.map(PopulatedWordSet::toDomain) }

    override fun getWordSets(): Flow<List<WordSet>> =
        wordDao.getWordSets().map { it.map(PopulatedWordSet::toDomain) }

    override suspend fun sync() {
        val wordSets = wordApi.getAllWordSets()

        wordDao.insertWordSets(wordSets = wordSets.map { WordSetEntity(it.id, it.title, it.description) })
        wordDao.insertWordPairs(wordPairs = wordSets.map { wordSet -> wordSet.words.map { WordPairEntity(it.id, it.first, it.second, false) } }.flatten() )
        wordDao.insertWordTags(wordTags = wordSets.map { wordSet -> wordSet.tags.map { WordSetTagEntity(id = it.id, tag = it.tag) } }.flatten())
        wordDao.insertWordSetWordPairCrossRefs(wordSetWordPairCrossRefs = wordSets.map { wordSet -> wordSet.words.map { word -> WordSetWordPairCrossRef(WordSetId = wordSet.id, WordPairId = word.id) } }.flatten())
        wordDao.insertWordSetWordTagCrossRefs(wordSetWordTagCrossRefs = wordSets.map { wordSet -> wordSet.tags.map { tag -> WordSetWordTagCrossRef(WordSetId = wordSet.id, WordTag = tag.id) } }.flatten())
    }
}