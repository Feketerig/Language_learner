package hu.bme.aut.android.languagelearner.data.network.dto

@kotlinx.serialization.Serializable
data class NewTokenResponseDTO(
    val accessToken: String,
    val refreshToken: String,
)