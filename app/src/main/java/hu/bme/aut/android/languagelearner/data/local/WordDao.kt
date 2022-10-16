package hu.bme.aut.android.languagelearner.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWordSets(wordSets: List<WordSetEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWordPairs(wordPairs: List<WordPairEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWordTags(wordTags: List<WordSetTagEntity>): List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWordSetWordPairCrossRefs(wordSetWordPairCrossRefs: List<WordSetWordPairCrossRef>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWordSetWordTagCrossRefs(wordSetWordTagCrossRefs: List<WordSetWordTagCrossRef>)

    @Transaction
    @Query("select * from word_sets")
    fun getWordSets(): Flow<List<PopulatedWordSet>>

    @Transaction
    @Query("select * from word_sets where id in (select word_set_id from word_sets_word_tags where word_tag_id in (select id from tags where tag in (:searchTags)))")
    fun getWordSets(searchTags: Set<String> = emptySet()): Flow<List<PopulatedWordSet>>
}