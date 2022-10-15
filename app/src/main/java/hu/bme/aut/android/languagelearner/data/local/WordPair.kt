package hu.bme.aut.android.languagelearner.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words")
data class WordPair(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val first: String,
    val second: String,
    val memorized: Boolean
)