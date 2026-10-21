package com.example.freegamesapi.ui.cats

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CatFactsScreen(
    viewModel: CatFactViewModel = viewModel()
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
            .padding(16.dp)
    ) {
        Text(
            text = "Datos Curiosos de Gatos (Cat Facts)",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = textCol
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Mostrando 10 datos (GET /facts?limit=10)",
            fontSize = 13.sp,
            color = mutedCol
        )
        Spacer(modifier = Modifier.height(16.dp))

        when (val state = uiState) {
            is CatFactUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = primaryPurp)
                }
            }
            is CatFactUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = state.message, color = Color(0xFFFCA5A5))
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { viewModel.cargarDatos() }) {
                            Icon(Icons.Default.Refresh, contentDescription = null)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Reintentar")
                        }
                    }
                }
            }
            is CatFactUiState.Success -> {
                if (state.facts.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No hay datos disponibles", color = mutedCol)
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        itemsIndexed(state.facts) { index, catFact ->
                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(16.dp),
                                color = cardCol,
                                border = BorderStroke(1.dp, borderCol)
                            ) {
                                Row(
                                    modifier = Modifier.padding(16.dp),
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .background(Color(0xFF22222D), RoundedCornerShape(10.dp)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "${index + 1}",
                                            color = primaryPurp,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(14.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = catFact.fact,
                                            fontSize = 15.sp,
                                            color = textCol,
                                            lineHeight = 20.sp
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = "Longitud: ${catFact.length} caracteres",
                                            fontSize = 12.sp,
                                            color = primaryPurp,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
