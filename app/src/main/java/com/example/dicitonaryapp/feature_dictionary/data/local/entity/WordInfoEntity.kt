package com.example.dicitonaryapp.feature_dictionary.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dicitonaryapp.feature_dictionary.domain.model.Meaning
import com.example.dictionary.feature_dictionary.domain.model.WordInfo

@Entity(tableName = "WordInfoTable")
data class WordInfoEntity(
    @PrimaryKey
    val id: Int? = null,
    val meanings: List<Meaning>,
    val phonetic: String?,
    val word: String
) {
    fun toWordInfo(): WordInfo {
        return WordInfo(meaning = meanings, phonetic = phonetic, word = word)
    }
}
