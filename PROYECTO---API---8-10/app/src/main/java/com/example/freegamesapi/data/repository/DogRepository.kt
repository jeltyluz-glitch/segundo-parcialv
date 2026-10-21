package com.example.freegamesapi.data.repository

import com.example.freegamesapi.data.mapper.ApiDataMapper
import com.example.freegamesapi.data.remote.api.DogCeoApiService
import com.example.freegamesapi.model.Breed
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DogRepository(private val api: DogCeoApiService) {
    suspend fun getBreeds(): Result<List<Breed>> = withContext(Dispatchers.IO) {
        try {
            val response = api.getAllBreeds()
            val breeds = ApiDataMapper.toBreedList(response)
            Result.success(breeds)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
