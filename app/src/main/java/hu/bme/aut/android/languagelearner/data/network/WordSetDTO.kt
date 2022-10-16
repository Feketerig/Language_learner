package hu.bme.aut.android.languagelearner.data.network

import kotlinx.serialization.Serializable

@Serializable
data class WordSetDTO (
    val id: Int,
    val title: String,
    val description: String,
    val words: List<WordPairDTO>,
    val tags: List<WordSetTagDTO>
)