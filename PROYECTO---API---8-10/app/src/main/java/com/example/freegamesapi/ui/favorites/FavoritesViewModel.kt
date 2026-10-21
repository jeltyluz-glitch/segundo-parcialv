package com.example.freegamesapi.ui.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.freegamesapi.data.local.database.AppDatabase
import com.example.freegamesapi.data.local.entity.RazaFavoritaEntity
import com.example.freegamesapi.data.repository.FavoritosRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).razaFavoritaDao()
    private val repository = FavoritosRepository(dao)

    val favoritos: StateFlow<List<RazaFavoritaEntity>> = repository.favoritosFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun eliminarFavorito(nombre: String) {
        viewModelScope.launch {
            repository.eliminarFavorito(nombre)
        }
    }
}
