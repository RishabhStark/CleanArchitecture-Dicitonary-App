package com.example.dicitonaryapp.feature_dictionary.domain.use_cases

import com.example.dicitonaryapp.core.util.Resource
import com.example.dictionary.feature_dictionary.domain.model.WordInfo
import com.example.dicitonaryapp.feature_dictionary.domain.repository.WordInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetWordInfoUseCase(private val repository: WordInfoRepository) {
    operator fun invoke(word: String): Flow<Resource<List<WordInfo>>> {
        if (word.isBlank()) return flow {  }
        return repository.getWordInfo(word = word)
    }
}