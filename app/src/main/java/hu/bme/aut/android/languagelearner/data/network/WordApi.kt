package hu.bme.aut.android.languagelearner.data.network

interface WordApi {

    suspend fun getAllWordSets(): List<WordSetDTO>
}