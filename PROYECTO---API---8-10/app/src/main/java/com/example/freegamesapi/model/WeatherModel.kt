package com.example.freegamesapi.model

data class WeatherModel(
    val temperature: Double,
    val humidity: Int,
    val windSpeed: Double,
    val time: String
)
