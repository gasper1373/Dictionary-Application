package com.example.dictionayapplication.presentation

import com.example.dictionayapplication.domain.model.WordItem

data class MainState(
    val isLoading: Boolean = false,
    val searchWord: String = "",
    val wordItem: WordItem? = null,
    val isSearchPerformed: Boolean = false,
    val isWordPlaying : Boolean = false
)
