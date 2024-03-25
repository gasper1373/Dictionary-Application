package com.example.dictionayapplication.data.mapper

import com.example.dictionayapplication.data.dto.DefinitionDto
import com.example.dictionayapplication.data.dto.MeaningDto
import com.example.dictionayapplication.data.dto.PhoneticDto
import com.example.dictionayapplication.data.dto.WordItemDto
import com.example.dictionayapplication.domain.model.WordItem
import com.example.dictionayapplication.domain.model.Meaning
import com.example.dictionayapplication.domain.model.Definition
import com.example.dictionayapplication.domain.model.Phonetic


fun WordItemDto.toWordItem() = WordItem(
    word = word ?: "",
    meanings = meanings?.map {
        it.toMeaning()
    } ?: emptyList(),
    phonetic = phonetic ?: "",
    phonetics = phonetics?.map {
        it.toPhonetic()
    }?: emptyList()
)

fun MeaningDto.toMeaning() = Meaning(
    definition = definitionDtoToDefinition(definitions?.get(0)),
    partOfSpeech = partOfSpeech ?: ""
)

fun definitionDtoToDefinition(definitionDto: DefinitionDto?) = Definition(
    definition = definitionDto?.definition ?: "",
    example = definitionDto?.example ?: ""
)

fun PhoneticDto.toPhonetic() = Phonetic(
    audio = audio ?: "",
)
