package hu.bme.aut.android.languagelearner.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TagDao {

    @Upsert
    suspend fun upsertWordTags(wordTags: List<WordTagEntity>)

    @Query("select id from tags where tag = :tag")
    suspend fun getTagIdByTag(tag: String): Int?

    @Query("select * from tags")
    fun getTags(): Flow<List<WordTagEntity>>

    @Query("delete from tags")
    fun clearTags()
}