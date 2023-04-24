package com.example.dicitonaryapp.feature_dictionary.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dicitonaryapp.feature_dictionary.domain.use_cases.GetWordInfoUseCase

class WordInfoViewModelFactory(private val myParameter: GetWordInfoUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordInfoViewModel::class.java)) {
            return WordInfoViewModel(myParameter) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}