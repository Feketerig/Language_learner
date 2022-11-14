package hu.bme.aut.android.languagelearner.domain.model

import kotlinx.datetime.Instant


data class WordSet(
    val id: Int,
    val title: String,
    val description: String,
    val deadline: Instant,
    val words: List<WordPair>,
    val tags: List<WordTag>
)
