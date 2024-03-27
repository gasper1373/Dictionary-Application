package com.example.dictionayapplication.domain.model

data class WordItem(
    val word: String,
    val meanings: List<Meaning>,
    val phonetic: String,
    val phonetics: List<Phonetic>?
){
    val audioUrl :String
        get() = phonetics?.firstOrNull{!it.audio.isNullOrEmpty()}?.audio ?: ""
}