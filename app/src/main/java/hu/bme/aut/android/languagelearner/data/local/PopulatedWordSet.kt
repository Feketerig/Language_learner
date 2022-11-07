package hu.bme.aut.android.languagelearner.data.local

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class PopulatedWordSet(
    @Embedded
    val wordSet: WordSetEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = WordSetWordPairCrossRef::class,
            parentColumn = "word_set_id",
            entityColumn = "word_pair_id"
        )
    )
    val words: List<WordPairEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = WordSetWordTagCrossRef::class,
            parentColumn = "word_set_id",
            entityColumn = "word_tag_id"
        )
    )
    val tags: List<WordTagEntity>
)
