package com.example.freegamesapi.data.remote.dto

import com.google.gson.annotations.SerializedName

data class WeatherResponseDto(
    @SerializedName("current") val current: CurrentWeatherDto?
)

data class CurrentWeatherDto(
    @SerializedName("temperature_2m") val temperature2m: Double?,
    @SerializedName("relative_humidity_2m") val relativeHumidity2m: Int?,
    @SerializedName("wind_speed_10m") val windSpeed10m: Double?,
    @SerializedName("time") val time: String?
)
