package com.example.freegamesapi.data.repository

import com.example.freegamesapi.data.local.dao.RazaFavoritaDao
import com.example.freegamesapi.data.local.entity.RazaFavoritaEntity
import kotlinx.coroutines.flow.Flow

class FavoritosRepository(private val dao: RazaFavoritaDao) {

    val favoritosFlow: Flow<List<RazaFavoritaEntity>> = dao.observarFavoritos()

    suspend fun agregarFavorito(entity: RazaFavoritaEntity) {
        dao.insertarFavorito(entity)
    }

    suspend fun eliminarFavorito(nombre: String) {
        dao.eliminarFavorito(nombre)
    }

    suspend fun esFavorito(nombre: String): Boolean {
        return dao.esFavorito(nombre)
    }
}
