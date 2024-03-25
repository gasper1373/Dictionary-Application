package com.example.dictionayapplication.data.api

import com.example.dictionayapplication.data.dto.WordResultDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ServiceApi {

    companion object{
        const val BASE_URL = "https://api.dictionaryapi.dev/api/v2/entries/en/"
    }


    @GET("{word}")
    suspend fun getWordResult(
        @Path("word")word :String
    ): WordResultDto?


}