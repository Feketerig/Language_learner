package hu.bme.aut.android.languagelearner.domain.repository

import hu.bme.aut.android.languagelearner.domain.model.WordPair
import hu.bme.aut.android.languagelearner.domain.model.WordSet
import hu.bme.aut.android.languagelearner.domain.model.WordTag
import kotlinx.coroutines.flow.Flow

interface WordRepository {

    suspend fun getWordSets(
        searchQuery: String,
        searchTags: List<Int>
    ): Flow<List<WordSet>>

    suspend fun sync()

    suspend fun getWordsBySetId(id: Int): List<WordPair>

    fun getTags(): Flow<List<WordTag>>

    suspend fun wordMemorizedChanged(wordPairId: Int, memorized: Boolean)

    suspend fun sendScore(courseId: Int, score: Int)

    suspend fun clearAll()
}