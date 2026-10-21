package com.example.freegamesapi.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.freegamesapi.data.local.entity.RazaFavoritaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RazaFavoritaDao {

    @Query("SELECT * FROM razas_favoritas")
    fun observarFavoritos(): Flow<List<RazaFavoritaEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarFavorito(entity: RazaFavoritaEntity)

    @Query("DELETE FROM razas_favoritas WHERE nombre = :nombre")
    suspend fun eliminarFavorito(nombre: String)

    @Query("SELECT EXISTS(SELECT 1 FROM razas_favoritas WHERE nombre = :nombre)")
    suspend fun esFavorito(nombre: String): Boolean

    @Query("SELECT COUNT(*) FROM razas_favoritas")
    suspend fun contarFavoritos(): Int
}
