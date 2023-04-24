package com.example.dicitonaryapp.feature_dictionary.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dicitonaryapp.core.util.Error
import com.example.dicitonaryapp.core.util.Loading
import com.example.dicitonaryapp.core.util.Resource
import com.example.dicitonaryapp.core.util.Success
import com.example.dictionary.feature_dictionary.domain.model.WordInfo
import com.example.dicitonaryapp.feature_dictionary.domain.use_cases.GetWordInfoUseCase
import com.example.dictionary.feature_dictionary.presentation.UiEvent
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class WordInfoViewModel(private val getWordInfoUseCase: GetWordInfoUseCase) : ViewModel() {
    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    private val _state = mutableStateOf(WordInfoState())
    val state: State<WordInfoState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var searchJob: Job? = null
    fun onSearch(query: String) {
        _searchQuery.value = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L)
            getWordInfoUseCase(query).onEach { result ->
                handleGetWordInfoUseCaseResult(result = result)
            }.launchIn(this)
        }
    }

    private suspend fun handleGetWordInfoUseCaseResult(result: Resource<List<WordInfo>>) {
        when (result) {
            is Success -> {
                _state.value = state.value.copy(
                    wordInfoItem = result.data ?: emptyList(),
                    isLoading = false
                )
            }
            is Error -> {
                _state.value = state.value.copy(
                    wordInfoItem = result.data ?: emptyList(),
                    isLoading = false
                )
                _eventFlow.emit(UiEvent.ShowSnackbar(message = result.message ?: "Unknown Error!"))
            }
            is Loading -> {
                _state.value = state.value.copy(
                    wordInfoItem = result.data ?: emptyList(),
                    isLoading = true
                )
            }
        }
    }
}