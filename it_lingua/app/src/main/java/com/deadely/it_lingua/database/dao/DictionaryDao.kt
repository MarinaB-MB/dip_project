package com.deadely.it_lingua.database.dao

import androidx.room.*
import com.deadely.it_lingua.model.Word
import com.deadely.it_lingua.utils.DICTIONARY
import io.reactivex.Single

@Dao
interface DictionaryDao {
    @Query("SELECT * FROM $DICTIONARY")
    fun getWords(): Single<List<Word>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addWord(word: Word)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addWordsList(words: List<Word>)

    @Query("DELETE FROM $DICTIONARY")
    fun clearWords()

    @Delete
    fun deleteWord(word: Word)
}
