package com.example.freegamesapi.data.mapper

import com.example.freegamesapi.data.local.entity.RazaFavoritaEntity
import com.example.freegamesapi.model.Breed

object LocalDataMapper {
    fun toEntity(breed: Breed): RazaFavoritaEntity {
        return RazaFavoritaEntity(
            nombre = breed.nombre,
            cantidadSubrazas = breed.cantidadSubrazas
        )
    }

    fun toDomain(entity: RazaFavoritaEntity): Breed {
        return Breed(
            nombre = entity.nombre,
            subrazas = emptyList(),
            cantidadSubrazas = entity.cantidadSubrazas,
            isFavorite = true
        )
    }
}
