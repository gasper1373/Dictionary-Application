package com.example.dictionayapplication.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionayapplication.domain.repository.DictionaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.dictionayapplication.util.Result

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dictionaryRepository: DictionaryRepository
) : ViewModel() {
    private val _mainState = MutableStateFlow(MainState())
    val mainState = _mainState.asStateFlow()
    private var searchJob: Job? = null

    fun onEvent(mainUiEvent: MainUiEvent) {
        when (mainUiEvent) {
            is MainUiEvent.OnSearchWordChange -> {
                _mainState.update {
                    it.copy(searchWord = mainUiEvent.newWord.lowercase())
                }
            }
            MainUiEvent.onSearchClick -> {
                searchJob?.cancel()
                _mainState.value = _mainState.value.copy(isSearchPerformed = true)
                searchJob = viewModelScope.launch {
                    loadWordResult()
                }
            }
        }
    }

    private fun loadWordResult() {
        viewModelScope.launch {
            dictionaryRepository.getWordResult(
                mainState.value.searchWord
            ).collect { result ->
                when (result) {
                    is Result.Error -> _mainState.update {
                        mainState -> mainState.copy(showSnackBar = true)
                    }
                    is Result.Loading -> {
                        _mainState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }
                    is Result.Success -> {
                        result.data?.let { wordItem ->
                            _mainState.update {
                                it.copy(
                                    wordItem = wordItem
                                )
                            }
                        }
                    }
                }
            }
        }
    }
    fun resetSnackbarShown(){
        _mainState.update { mainState ->
            mainState.copy(showSnackBar = false)
        }
    }
}