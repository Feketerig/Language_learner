package hu.bme.aut.android.languagelearner.data.network

import hu.bme.aut.android.languagelearner.data.network.dto.LoginResponse
import hu.bme.aut.android.languagelearner.data.network.dto.WordPairDTO
import hu.bme.aut.android.languagelearner.data.network.dto.WordSetDTO

interface WordApi {

    suspend fun login(): LoginResponse



    suspend fun getAllCourses(): List<WordSetDTO>

    suspend fun getAllWordsByCourseId(id: Int): List<WordPairDTO>

    suspend fun sendScore(courseId: Int, score: Int)

    suspend fun updateWordMemorized(wordPairId: Int, memorized: Boolean)

    companion object{
        const val BASE_URL = "http://10.0.2.2:8080/api"
    }

    sealed class Endpoints(val url: String){
        object Auth: Endpoints("$BASE_URL/auth")
        object Words: Endpoints("$BASE_URL/wordPair")
        object Course: Endpoints("$BASE_URL/course")
    }
}