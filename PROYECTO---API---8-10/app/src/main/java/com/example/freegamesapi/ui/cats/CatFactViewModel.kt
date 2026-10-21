package com.example.freegamesapi.ui.cats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.freegamesapi.data.remote.RetrofitClient
import com.example.freegamesapi.data.repository.CatFactRepository
import com.example.freegamesapi.model.CatFact
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface CatFactUiState {
    object Loading : CatFactUiState
    data class Success(val facts: List<CatFact>) : CatFactUiState
    data class Error(val message: String) : CatFactUiState
}

class CatFactViewModel : ViewModel() {
    private val repository = CatFactRepository(RetrofitClient.catFactApi)

    private val _uiState = MutableStateFlow<CatFactUiState>(CatFactUiState.Loading)
    val uiState: StateFlow<CatFactUiState> = _uiState.asStateFlow()

    init {
        cargarDatos()
    }

    fun cargarDatos() {
        viewModelScope.launch {
            _uiState.value = CatFactUiState.Loading
            repository.getFacts()
                .onSuccess { facts ->
                    _uiState.value = CatFactUiState.Success(facts)
                }
                .onFailure { error ->
                    _uiState.value = CatFactUiState.Error(error.localizedMessage ?: "Error al cargar datos curiosos de gatos")
                }
        }
    }
}
