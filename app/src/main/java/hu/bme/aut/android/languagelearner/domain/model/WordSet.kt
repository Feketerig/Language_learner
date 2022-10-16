package hu.bme.aut.android.languagelearner.domain.model

data class WordSet(
    val id: Int,
    val title: String,
    val description: String,
    val words: List<WordPair>,
    val tags: List<String>
)
