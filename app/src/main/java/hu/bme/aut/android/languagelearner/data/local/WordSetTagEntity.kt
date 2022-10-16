package hu.bme.aut.android.languagelearner.data.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "tags",
    indices = [Index(value = ["tag"])]
)
data class WordSetTagEntity (
    @PrimaryKey
    val id: Int,
    val tag: String
)
