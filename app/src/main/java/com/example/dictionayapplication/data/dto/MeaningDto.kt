package com.example.dictionayapplication.data.dto

data class MeaningDto(
    val definitions: List<DefinitionDto>,
    val partOfSpeech: String,
    //  val antonyms: List<String>,
    // val synonyms: List<String>
)