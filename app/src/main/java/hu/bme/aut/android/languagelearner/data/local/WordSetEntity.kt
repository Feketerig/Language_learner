package hu.bme.aut.android.languagelearner.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_sets")
data class WordSetEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val description: String,
    val deadline: Long
)
