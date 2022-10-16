package hu.bme.aut.android.languagelearner.data.network

import kotlinx.serialization.Serializable

@Serializable
data class WordSetTagDTO(
    val id: Int,
    val tag: String
)
