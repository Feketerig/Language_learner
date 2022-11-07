package hu.bme.aut.android.languagelearner.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        WordSetEntity::class,
        WordSetWordPairCrossRef::class,
        WordPairEntity::class,
        WordSetWordTagCrossRef::class,
        WordTagEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class WordDatabase: RoomDatabase() {
    abstract val wordSetDao: WordSetDao
    abstract val wordDao: WordDao
    abstract val tagDao: TagDao
}