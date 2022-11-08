package hu.bme.aut.android.languagelearner.data.network

import hu.bme.aut.android.languagelearner.data.network.dto.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class WordApiImpl(
    private val client: HttpClient
): WordApi {
    /*override suspend fun getAllWordSets(): List<WordSetDTO> {
        return listOf(
            WordSetDTO(
                id = 1,
                title = "Title",
                description = "Description - optional",
                words = listOf(WordPairDTO(1,"magyar", "hungarian", false)),
                tags = listOf(WordSetTagDTO(10,"angol"), WordSetTagDTO(9,"magyar"))
            ),
            WordSetDTO(
                id = 2,
                title = "Title 2",
                description = "Description - optional",
                words = listOf(WordPairDTO(2,"angol", "english", true), WordPairDTO(1,"magyar", "hungarian", false)),
                tags = listOf(WordSetTagDTO(5,"német"), WordSetTagDTO(8,"magyar"))
            ),
            WordSetDTO(
                id = 3,
                title = "Title 3",
                description = "Description - optional",
                words = listOf(WordPairDTO(1,"magyar", "hungarian", false)),
                tags = listOf(WordSetTagDTO(1,"angol"), WordSetTagDTO(2,"magyar"))
            )
        )
        return emptyList()
    }*/

    override suspend fun updateWordMemorized(wordPairId: Int, memorized: Boolean) {

    }

    override suspend fun login(email: String, password: String): LoginResponse {
        return client.post(urlString = WordApi.Endpoints.Auth.url + "/login"){
            setBody(LoginRequestDTO(email, password))
            //setBody(LoginRequestDTO("asd2@asd.asd", "rM8ax1hXrk"))
            //setBody(LoginRequestDTO("asd3@asd.asd", "TvX-gl1pNv"))
            //setBody(LoginRequestDTO("asd@asd.asd", "12345678"))
        }.body()
    }

    override suspend fun logout(refreshToken: RefreshTokenRequestDTO) {
        client.post(WordApi.Endpoints.Auth.url + "/logout"){
            setBody(refreshToken)
        }
    }

    override suspend fun getAllCourses(): List<WordSetDTO> {
        return client.get(WordApi.Endpoints.Course.url + "/student/all").body()
    }

    override suspend fun getAllWordsByCourseId(id: Int): List<WordPairDTO> {
        return client.get(WordApi.Endpoints.Words.url + "/student/words/" + id.toString()).body()
    }

    override suspend fun sendScore(courseId: Int, score: Int) {
        client.post(WordApi.Endpoints.Course.url + "/submit"){
            setBody(SubmissionRequestDTO(
                courseId = courseId,
                score = score
            ))
        }
    }

    override suspend fun setName(name: String){
        client.post(WordApi.Endpoints.Auth.url + "/name"){
            setBody(name)
        }
    }
}