package com.example.dictionayapplication.presentation

sealed class MainUiEvent {
    data class OnSearchWordChange(
        val newWord :String
    ): MainUiEvent()
    object onSearchClick: MainUiEvent()
}