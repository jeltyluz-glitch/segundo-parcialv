package com.example.freegamesapi.ui.dogs

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.freegamesapi.data.local.database.AppDatabase
import com.example.freegamesapi.data.mapper.LocalDataMapper
import com.example.freegamesapi.data.remote.RetrofitClient
import com.example.freegamesapi.data.repository.DogRepository
import com.example.freegamesapi.data.repository.FavoritosRepository
import com.example.freegamesapi.model.Breed
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface DogUiState {
    object Loading : DogUiState
    data class Success(val breeds: List<Breed>) : DogUiState
    data class Error(val message: String) : DogUiState
}

class DogViewModel(application: Application) : AndroidViewModel(application) {
    private val favoritosDao = AppDatabase.getDatabase(application).razaFavoritaDao()
    private val favoritosRepository = FavoritosRepository(favoritosDao)
    private val dogRepository = DogRepository(RetrofitClient.dogCeoApi)

    private val _uiState = MutableStateFlow<DogUiState>(DogUiState.Loading)
    val uiState: StateFlow<DogUiState> = _uiState.asStateFlow()

    private var allBreeds = listOf<Breed>()
    private var favoriteNames = setOf<String>()

    init {
        observarFavoritosYCargarRazas()
    }

    private fun observarFavoritosYCargarRazas() {
        viewModelScope.launch {
            favoritosRepository.favoritosFlow.collect { favorites ->
                favoriteNames = favorites.map { it.nombre }.toSet()
                updateBreedsWithFavorites()
            }
        }
        cargarRazas()
    }

    fun cargarRazas() {
        viewModelScope.launch {
            if (allBreeds.isEmpty()) {
                _uiState.value = DogUiState.Loading
            }
            dogRepository.getBreeds()
                .onSuccess { breeds ->
                    allBreeds = breeds
                    updateBreedsWithFavorites()
                    _uiState.value = DogUiState.Success(allBreeds)
                }
                .onFailure { error ->
                    if (allBreeds.isEmpty()) {
                        _uiState.value = DogUiState.Error(error.localizedMessage ?: "Error al cargar razas de perros")
                    }
                }
        }
    }

    private fun updateBreedsWithFavorites() {
        allBreeds = allBreeds.map { breed ->
            breed.copy(isFavorite = favoriteNames.contains(breed.nombre))
        }
        if (_uiState.value is DogUiState.Success || allBreeds.isNotEmpty()) {
            _uiState.value = DogUiState.Success(allBreeds)
        }
    }

    fun toggleFavorito(breed: Breed) {
        viewModelScope.launch {
            if (breed.isFavorite) {
                favoritosRepository.eliminarFavorito(breed.nombre)
            } else {
                val entity = LocalDataMapper.toEntity(breed)
                favoritosRepository.agregarFavorito(entity)
            }
        }
    }

    fun getBreedByName(name: String): Breed? {
        return allBreeds.find { it.nombre.equals(name, ignoreCase = true) }
    }
}
