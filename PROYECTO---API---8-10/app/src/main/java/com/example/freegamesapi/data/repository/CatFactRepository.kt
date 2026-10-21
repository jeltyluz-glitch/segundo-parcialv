package com.example.freegamesapi.data.repository

import com.example.freegamesapi.data.mapper.ApiDataMapper
import com.example.freegamesapi.data.remote.api.CatFactApiService
import com.example.freegamesapi.model.CatFact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CatFactRepository(private val api: CatFactApiService) {
    suspend fun getFacts(): Result<List<CatFact>> = withContext(Dispatchers.IO) {
        try {
            val response = api.getFacts(10)
            val facts = ApiDataMapper.toCatFactList(response)
            Result.success(facts)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
