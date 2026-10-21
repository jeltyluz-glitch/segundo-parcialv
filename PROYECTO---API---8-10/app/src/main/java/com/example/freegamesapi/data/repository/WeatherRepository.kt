package com.example.freegamesapi.data.repository

import com.example.freegamesapi.data.mapper.ApiDataMapper
import com.example.freegamesapi.data.remote.api.OpenMeteoApiService
import com.example.freegamesapi.model.WeatherModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepository(private val api: OpenMeteoApiService) {
    suspend fun getWeather(): Result<WeatherModel> = withContext(Dispatchers.IO) {
        try {
            val response = api.getForecast()
            val model = ApiDataMapper.toWeatherModel(response)
            Result.success(model)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
