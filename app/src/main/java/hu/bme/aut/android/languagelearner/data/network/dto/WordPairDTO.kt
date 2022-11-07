package hu.bme.aut.android.languagelearner.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class WordPairDTO(
    val id: Int,
    val word: String,
    val translation: String,
    val metadata: List<String>
)
