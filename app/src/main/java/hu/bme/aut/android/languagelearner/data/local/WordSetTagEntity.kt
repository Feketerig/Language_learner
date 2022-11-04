package hu.bme.aut.android.languagelearner.data.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "tags",
    indices = [Index(value = ["tag"], unique = true)]
)
data class WordSetTagEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val tag: String
)
