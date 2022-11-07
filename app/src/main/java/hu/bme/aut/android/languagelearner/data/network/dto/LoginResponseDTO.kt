package hu.bme.aut.android.languagelearner.data.network.dto

@kotlinx.serialization.Serializable
data class LoginResponse(
    val userDetails: UserDetailsResponse,
    val accessToken: String,
    val refreshToken: String
)

@kotlinx.serialization.Serializable
data class UserDetailsResponse(
     val id: Int? = null,
     val name: String? = null,
     val email: String? = null,
     val roles: List<String>? = null,
)