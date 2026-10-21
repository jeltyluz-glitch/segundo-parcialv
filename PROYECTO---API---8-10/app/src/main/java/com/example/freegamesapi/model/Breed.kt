package com.example.freegamesapi.model

data class Breed(
    val nombre: String,
    val subrazas: List<String>,
    val cantidadSubrazas: Int,
    val isFavorite: Boolean = false
)
