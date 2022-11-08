package hu.bme.aut.android.languagelearner.data.local

import androidx.room.*

@Dao
interface WordDao {

    @Upsert
    suspend fun upsertWordPairs(wordPairs: List<WordPairEntity>)

    @Query("select * from words where id in (select word_pair_id from word_sets_word_pairs where word_set_id = :id)")
    suspend fun getWordPairsBySetId(id: Int): List<WordPairEntity>

    @Query("update words set memorized = :memorized where id = :id")
    suspend fun updateWordMemorized(id: Int, memorized: Boolean)

    @Query("delete from words")
    fun clearWords()
}
