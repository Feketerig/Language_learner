package hu.bme.aut.android.languagelearner.data.network.dto

@kotlinx.serialization.Serializable
data class RefreshTokenRequestDTO(
    val refreshToken: String
)