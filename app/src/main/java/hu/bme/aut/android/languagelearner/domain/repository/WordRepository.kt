package hu.bme.aut.android.languagelearner.domain.repository

import hu.bme.aut.android.languagelearner.domain.model.WordSet
import kotlinx.coroutines.flow.Flow

interface WordRepository {

    fun getWordSets(
        searchTags: Set<String>
    ): Flow<List<WordSet>>

    fun getWordSets(): Flow<List<WordSet>>

    suspend fun sync()
}