package com.example.freegamesapi.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "razas_favoritas")
data class RazaFavoritaEntity(
    @PrimaryKey
    val nombre: String,
    val cantidadSubrazas: Int
)
