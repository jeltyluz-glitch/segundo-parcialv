package com.example.freegamesapi.ui.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.freegamesapi.data.remote.RetrofitClient
import com.example.freegamesapi.data.repository.WeatherRepository
import com.example.freegamesapi.model.WeatherModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface WeatherUiState {
    object Loading : WeatherUiState
    data class Success(val weather: WeatherModel) : WeatherUiState
    data class Error(val message: String) : WeatherUiState
}

class WeatherViewModel : ViewModel() {
    private val repository = WeatherRepository(RetrofitClient.openMeteoApi)

    private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Loading)
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    init {
        cargarClima()
    }

    fun cargarClima() {
        viewModelScope.launch {
            _uiState.value = WeatherUiState.Loading
            repository.getWeather()
                .onSuccess { weather ->
                    _uiState.value = WeatherUiState.Success(weather)
                }
                .onFailure { error ->
                    _uiState.value = WeatherUiState.Error(error.localizedMessage ?: "Error al conectar con Open-Meteo")
                }
        }
    }
}
