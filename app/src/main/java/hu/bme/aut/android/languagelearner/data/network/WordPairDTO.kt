package hu.bme.aut.android.languagelearner.data.network

import kotlinx.serialization.Serializable

@Serializable
data class WordPairDTO(
    val id: Int,
    val first: String,
    val second: String,
    val memorized: Boolean
)
