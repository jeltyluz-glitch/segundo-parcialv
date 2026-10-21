package com.example.freegamesapi.data.remote.dto

import com.google.gson.annotations.SerializedName

data class DogBreedsResponseDto(
    @SerializedName("message") val message: Map<String, List<String>>?,
    @SerializedName("status") val status: String?
)
