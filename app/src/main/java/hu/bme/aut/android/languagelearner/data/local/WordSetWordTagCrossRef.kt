package hu.bme.aut.android.languagelearner.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "word_sets_word_tags",
    primaryKeys = ["word_set_id","word_tag_id"],
    foreignKeys = [
        ForeignKey(
            entity = WordSetEntity::class,
            parentColumns = ["id"],
            childColumns = ["word_set_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = WordTagEntity::class,
            parentColumns = ["id"],
            childColumns = ["word_tag_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["word_set_id"]),
        Index(value = ["word_tag_id"]),
    ]
)
data class WordSetWordTagCrossRef (
    @ColumnInfo(name = "word_set_id")
    val WordSetId: Int,
    @ColumnInfo(name = "word_tag_id")
    val WordTag: Int
)