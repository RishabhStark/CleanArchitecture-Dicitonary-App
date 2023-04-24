package com.example.dicitonaryapp.feature_dictionary.domain.repository

import com.example.dicitonaryapp.core.util.Resource
import com.example.dictionary.feature_dictionary.domain.model.WordInfo
import kotlinx.coroutines.flow.Flow

interface WordInfoRepository {
     fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>>
}