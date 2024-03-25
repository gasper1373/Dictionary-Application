package com.example.dictionayapplication.data.dto

import com.example.dictionayapplication.domain.model.Phonetic

data class WordItemDto(
    val meanings: List<MeaningDto>? = null,
    val word: String? = null,
    // val license: License,
    val phonetic: String? = null,
    val phonetics: List<PhoneticDto>? = null,

    )