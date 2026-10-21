package com.example.freegamesapi.data.remote.api

import com.example.freegamesapi.data.remote.dto.CatFactResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CatFactApiService {
    @GET("facts")
    suspend fun getFacts(@Query("limit") limit: Int = 10): CatFactResponseDto
}
