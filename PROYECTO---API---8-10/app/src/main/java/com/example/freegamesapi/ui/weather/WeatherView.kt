package com.example.freegamesapi.ui.weather

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val bgCol = Color(0xFF0F0F13)
    val cardCol = Color(0xFF181820)
    val primaryPurp = Color(0xFFC4B5FD)
    val textCol = Color.White
    val mutedCol = Color(0xFF9CA3AF)
    val borderCol = Color(0xFF2A2A38)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgCol)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Clima en La Paz (Open-Meteo)",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = textCol
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Lat: -16.5, Lon: -68.15 (TimeZone: America/La_Paz)",
            fontSize = 13.sp,
            color = mutedCol
        )
        Spacer(modifier = Modifier.height(24.dp))

        when (val state = uiState) {
            is WeatherUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = primaryPurp)
                }
            }
            is WeatherUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = state.message, color = Color(0xFFFCA5A5))
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { viewModel.cargarClima() }) {
                            Text("Reintentar")
                        }
                    }
                }
            }
            is WeatherUiState.Success -> {
                val weather = state.weather
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    WeatherInfoCard(
                        title = "Temperatura Actual",
                        value = "${weather.temperature} °C",
                        icon = Icons.Default.Thermostat,
                        cardCol = cardCol,
                        borderCol = borderCol,
                        textCol = textCol,
                        primaryPurp = primaryPurp
                    )

                    WeatherInfoCard(
                        title = "Humedad Relativa",
                        value = "${weather.humidity} %",
                        icon = Icons.Default.WaterDrop,
                        cardCol = cardCol,
                        borderCol = borderCol,
                        textCol = textCol,
                        primaryPurp = primaryPurp
                    )

                    WeatherInfoCard(
                        title = "Velocidad del Viento",
                        value = "${weather.windSpeed} km/h",
                        icon = Icons.Default.Air,
                        cardCol = cardCol,
                        borderCol = borderCol,
                        textCol = textCol,
                        primaryPurp = primaryPurp
                    )

                    WeatherInfoCard(
                        title = "Última Medición (Time)",
                        value = weather.time,
                        icon = Icons.Default.Cloud,
                        cardCol = cardCol,
                        borderCol = borderCol,
                        textCol = textCol,
                        primaryPurp = primaryPurp
                    )
                }
            }
        }
    }
}

@Composable
fun WeatherInfoCard(
    title: String,
    value: String,
    icon: ImageVector,
    cardCol: Color,
    borderCol: Color,
    textCol: Color,
    primaryPurp: Color
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = cardCol,
        border = BorderStroke(1.dp, borderCol)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Color(0xFF22222D), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = primaryPurp)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = title, fontSize = 13.sp, color = Color(0xFF9CA3AF))
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = value, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = textCol)
            }
        }
    }
}
