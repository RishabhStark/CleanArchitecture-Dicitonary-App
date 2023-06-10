package com.example.dicitonaryapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.dicitonaryapp.feature_dictionary.domain.use_cases.GetWordInfoUseCase
import com.example.dicitonaryapp.ui.theme.DictionaryAppTheme
import com.example.dictionary.feature_dictionary.presentation.UiEvent
import com.example.dictionary.feature_dictionary.presentation.WordInfoItem
import com.example.dicitonaryapp.feature_dictionary.presentation.WordInfoViewModel
import com.example.dicitonaryapp.feature_dictionary.presentation.WordInfoViewModelFactory
import com.example.dicitonaryapp.feature_dictionary.data.local.DictionaryDatabase
import com.example.dicitonaryapp.feature_dictionary.data.remote.RetrofitClient
import com.example.dicitonaryapp.feature_dictionary.data.repository.WordInfoRepositoryImpl
import kotlinx.coroutines.flow.collectLatest

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DictionaryAppTheme {
                val getWordInfoUseCase = GetWordInfoUseCase(
                    WordInfoRepositoryImpl(
                        DictionaryDatabase.getDatabase(this).dao,
                        RetrofitClient.dictionaryApiService
                    )
                )
                if (isSystemInDarkTheme()) {
                    window.statusBarColor = MaterialTheme.colors.background.toArgb()
                }
                val viewModelFactory = WordInfoViewModelFactory(getWordInfoUseCase)
                val dictionaryInfoViewModel: WordInfoViewModel = ViewModelProvider(
                    this, viewModelFactory
                )[WordInfoViewModel::class.java]
                val state = dictionaryInfoViewModel.state.value
                val scaffoldState = rememberScaffoldState()
                LaunchedEffect(key1 = true) {
                    dictionaryInfoViewModel.eventFlow.collectLatest { event ->
                        when (event) {
                            is UiEvent.ShowSnackbar -> {
                                scaffoldState.snackbarHostState.showSnackbar(event.message)
                            }
                        }
                    }
                }
                Scaffold(scaffoldState = scaffoldState) {
                    Box(modifier = Modifier.background(MaterialTheme.colors.background)) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {
                            TextField(value = dictionaryInfoViewModel.searchQuery.value,
                                onValueChange = dictionaryInfoViewModel::onSearch,
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = {
                                    Text(text = "Search...")
                                })
                            Spacer(modifier = Modifier.height(16.dp))
                            LazyColumn(modifier = Modifier.fillMaxSize()) {
                                items(state.wordInfoItem.size) { i ->
                                    val wordInfo = state.wordInfoItem[i]
                                    if (i > 0) {
                                        Spacer(modifier = Modifier.height(8.dp))
                                    }
                                    WordInfoItem(wordInfo = wordInfo)
                                    if (i < state.wordInfoItem.size - 1) {
                                        Divider()
                                    }


                                }
                            }
                        }
                    }
                }
            }
        }
    }
}