package hu.bme.aut.android.languagelearner.data.network.dto

@kotlinx.serialization.Serializable
data class LoginRequestDTO(
    val email: String,
    val password: String
)