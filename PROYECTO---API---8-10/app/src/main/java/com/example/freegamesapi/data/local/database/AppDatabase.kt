package com.example.freegamesapi.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.freegamesapi.data.local.dao.RazaFavoritaDao
import com.example.freegamesapi.data.local.entity.RazaFavoritaEntity

@Database(entities = [RazaFavoritaEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun razaFavoritaDao(): RazaFavoritaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "naturaleza_clima_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
