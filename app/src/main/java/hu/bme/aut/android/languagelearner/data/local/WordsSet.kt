package hu.bme.aut.android.languagelearner.data.local

import androidx.room.Entity

@Entity(tableName = "wordsSets")
data class WordsSet(
    val id: Int,
    val title: String,
    val description: String,
    val words: List<Int>,
    val tags: List<String>
)
