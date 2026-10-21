package com.example.freegamesapi.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CatFactResponseDto(
    @SerializedName("data") val data: List<CatFactItemDto>?
)

data class CatFactItemDto(
    @SerializedName("fact") val fact: String?,
    @SerializedName("length") val length: Int?
)
