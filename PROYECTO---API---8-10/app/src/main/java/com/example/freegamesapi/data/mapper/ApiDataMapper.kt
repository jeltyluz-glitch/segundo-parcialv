package com.example.freegamesapi.data.mapper

import com.example.freegamesapi.data.remote.dto.CatFactResponseDto
import com.example.freegamesapi.data.remote.dto.DogBreedsResponseDto
import com.example.freegamesapi.data.remote.dto.WeatherResponseDto
import com.example.freegamesapi.model.Breed
import com.example.freegamesapi.model.CatFact
import com.example.freegamesapi.model.WeatherModel

object ApiDataMapper {
    fun toWeatherModel(dto: WeatherResponseDto): WeatherModel {
        val current = dto.current
        return WeatherModel(
            temperature = current?.temperature2m ?: 0.0,
            humidity = current?.relativeHumidity2m ?: 0,
            windSpeed = current?.windSpeed10m ?: 0.0,
            time = current?.time ?: "Desconocida"
        )
    }

    fun toBreedList(dto: DogBreedsResponseDto, favoriteNames: Set<String> = emptySet()): List<Breed> {
        val messageMap = dto.message ?: return emptyList()
        return messageMap.map { (breedName, subBreeds) ->
            Breed(
                nombre = breedName,
                subrazas = subBreeds,
                cantidadSubrazas = subBreeds.size,
                isFavorite = favoriteNames.contains(breedName)
            )
        }
    }

    fun toCatFactList(dto: CatFactResponseDto): List<CatFact> {
        val list = dto.data ?: return emptyList()
        return list.map { item ->
            CatFact(
                fact = item.fact ?: "",
                length = item.length ?: 0
            )
        }
    }
}
