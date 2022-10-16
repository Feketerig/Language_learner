package hu.bme.aut.android.languagelearner.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        WordSetEntity::class,
        WordSetWordPairCrossRef::class,
        WordPairEntity::class,
        WordSetWordTagCrossRef::class,
        WordSetTagEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class WordDatabase: RoomDatabase() {
    abstract val wordDao: WordDao
}