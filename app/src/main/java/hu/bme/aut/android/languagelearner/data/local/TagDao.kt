package hu.bme.aut.android.languagelearner.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TagDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWordTags(wordTags: List<WordSetTagEntity>): List<Long>

    @Query("select id from tags where tag = :tag")
    suspend fun getTagIdByTag(tag: String): Int

    @Query("select * from tags")
    fun getTags(): Flow<List<WordSetTagEntity>>
}