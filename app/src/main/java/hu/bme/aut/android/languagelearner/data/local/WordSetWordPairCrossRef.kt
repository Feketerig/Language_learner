package hu.bme.aut.android.languagelearner.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "word_sets_word_pairs",
    primaryKeys = ["word_set_id","word_pair_id"],
    foreignKeys = [
        ForeignKey(
            entity = WordSetEntity::class,
            parentColumns = ["id"],
            childColumns = ["word_set_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = WordPairEntity::class,
            parentColumns = ["id"],
            childColumns = ["word_pair_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["word_set_id"]),
        Index(value = ["word_pair_id"]),
    ]
)
data class WordSetWordPairCrossRef (
    @ColumnInfo(name = "word_set_id")
    val WordSetId: Int,
    @ColumnInfo(name = "word_pair_id")
    val WordPairId: Int
)