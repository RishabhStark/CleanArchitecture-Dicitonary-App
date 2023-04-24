package com.example.dictionary.feature_dictionary.domain.model

import com.example.dicitonaryapp.feature_dictionary.domain.model.Meaning

data class WordInfo(
    val meaning: List<Meaning>,
    val phonetic: String?,
    val word: String
)
