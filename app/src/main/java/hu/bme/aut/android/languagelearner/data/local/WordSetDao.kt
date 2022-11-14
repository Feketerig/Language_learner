package hu.bme.aut.android.languagelearner.data.local

import androidx.room.*

@Dao
interface WordSetDao {

    @Upsert
    suspend fun upsertWordSets(wordSets: List<WordSetEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWordSetWordPairCrossRefs(wordSetWordPairCrossRefs: List<WordSetWordPairCrossRef>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWordSetWordTagCrossRefs(wordSetWordTagCrossRefs: List<WordSetWordTagCrossRef>)

    @Transaction
    @Query("select * from word_sets where LOWER(title) Like '%' || LOWER(:searchQuery) || '%' or LOWER(description) Like '%' || LOWER(:searchQuery) || '%'")
    suspend fun getWordSets(searchQuery: String): List<PopulatedWordSet>

    @Query("delete from word_sets")
    fun clearWordSets()

    @Query("delete from word_sets_word_pairs")
    fun clearWordSetWordPairCrossRefs()

    @Query("delete from word_sets_word_tags")
    fun clearWordSetWordTagCrossRefs()
}