package com.example.dictionary.feature_dictionary.presentation

sealed class UiEvent {
    data class ShowSnackbar(val message:String):UiEvent()
}
