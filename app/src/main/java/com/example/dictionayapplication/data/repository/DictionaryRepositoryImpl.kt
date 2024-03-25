package com.example.dictionayapplication.data.repository

import android.app.Application
import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.dictionayapplication.R
import com.example.dictionayapplication.data.api.ServiceApi
import com.example.dictionayapplication.data.mapper.toWordItem
import com.example.dictionayapplication.domain.model.WordItem
import com.example.dictionayapplication.domain.repository.DictionaryRepository
import com.example.dictionayapplication.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class DictionaryRepositoryImpl @Inject constructor(
    private val dictionaryApi: ServiceApi,
    private val application: Application
) : DictionaryRepository {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getWordResult(
        word: String
    ): Flow<Result<WordItem>> {
        return flow {
            emit(Result.Loading(true))
            val remoteWordResultDto = try {
                dictionaryApi.getWordResult(word)
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Result.Error(application.getString(R.string.there_is_a_problem)))
                emit(Result.Loading(false))
                return@flow
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Result.Error(application.getString(R.string.there_is_a_problem)))
                emit(Result.Loading(false))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(application.getString(R.string.there_is_a_problem)))
                emit(Result.Loading(false))
                return@flow
            }
            remoteWordResultDto?.let { wordResultDto ->
                wordResultDto[0]?.let { wordItemDto ->
                    emit(Result.Success(wordItemDto.toWordItem()))
                    emit(Result.Loading(false))
                    return@flow
                }
            }
            emit(Result.Error(application.getString(R.string.there_is_a_problem)))
            emit(Result.Loading(false))
        }
    }
}