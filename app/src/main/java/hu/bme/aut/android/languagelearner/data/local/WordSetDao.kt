package hu.bme.aut.android.languagelearner.data.local

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import kotlinx.coroutines.flow.Flow

@Dao
interface WordSetDao {

    @Upsert
    suspend fun upsertWordSets(wordSets: List<WordSetEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWordSetWordPairCrossRefs(wordSetWordPairCrossRefs: List<WordSetWordPairCrossRef>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWordSetWordTagCrossRefs(wordSetWordTagCrossRefs: List<WordSetWordTagCrossRef>)

    @RawQuery
    fun getWordSets(query: SupportSQLiteQuery): Flow<List<PopulatedWordSet>>

    @Transaction
    @Query("select * from word_sets where LOWER(title) Like '%' || LOWER(:searchQuery) || '%' or LOWER(description) Like '%' || LOWER(:searchQuery) || '%'")
    fun getWordSets(searchQuery: String): Flow<List<PopulatedWordSet>>

    @Transaction
    @Query("select * from word_sets where id in (select word_set_id from word_sets_word_tags where word_tag_id in (:searchTags)) and (LOWER(title) Like '%' || LOWER(:searchQuery) || '%' or LOWER(description) Like '%' || LOWER(:searchQuery) || '%')")
    fun getWordSets(searchQuery: String, searchTags: Set<Int>): Flow<List<PopulatedWordSet>>

    @Query("delete from word_sets")
    fun clearWordSets()

    @Query("delete from word_sets_word_pairs")
    fun clearWordSetWordPairCrossRefs()

    @Query("delete from word_sets_word_tags")
    fun clearWordSetWordTagCrossRefs()
}