package com.example.dictionayapplication.domain.repository

import com.example.dictionayapplication.domain.model.WordItem
import com.example.dictionayapplication.util.Result
import kotlinx.coroutines.flow.Flow

interface DictionaryRepository {
    suspend fun getWordResult(
        word : String
    ): Flow<Result<WordItem>>
}