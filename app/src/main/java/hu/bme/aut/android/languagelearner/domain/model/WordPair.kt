package hu.bme.aut.android.languagelearner.domain.model

data class WordPair(
    val id: Int,
    val first: String,
    val second: String,
    var memorized: Boolean
)
