package com.example.freegamesapi.data.remote.api

import com.example.freegamesapi.data.remote.dto.DogBreedsResponseDto
import retrofit2.http.GET

interface DogCeoApiService {
    @GET("breeds/list/all")
    suspend fun getAllBreeds(): DogBreedsResponseDto
}
