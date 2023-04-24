package com.example.dictionary.feature_dictionary.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dicitonaryapp.feature_dictionary.data.local.entity.WordInfoEntity

@Dao
interface WordInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWordInfos(list: List<WordInfoEntity>)

    @Query("DELETE FROM wordinfotable WHERE word in (:words)")
    suspend fun deleteWords(words:List<String>)

    @Query("SELECT * FROM wordinfotable WHERE word LIKE '%' || :word || '%'")
    suspend fun getWordInfos(word:String):List<WordInfoEntity>
}