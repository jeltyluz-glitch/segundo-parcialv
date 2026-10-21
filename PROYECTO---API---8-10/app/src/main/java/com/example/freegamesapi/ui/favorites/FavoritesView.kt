package com.example.freegamesapi.ui.favorites

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
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
fun FavoritesScreen(
    viewModel: FavoritesViewModel = viewModel(),
    onSelectBreed: (String) -> Unit
) {
    val favoritos by viewModel.favoritos.collectAsState()

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
            text = "Razas Favoritas (Room DB)",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = textCol
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Almacenamiento local persistente con Room",
            fontSize = 13.sp,
            color = mutedCol
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (favoritos.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Sin elementos guardados",
                    fontSize = 16.sp,
                    color = mutedCol,
                    fontWeight = FontWeight.Medium
                )
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(favoritos) { fav ->
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        color = cardCol,
                        border = BorderStroke(1.dp, borderCol)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = fav.nombre.replaceFirstChar { it.uppercase() },
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = textCol
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Subrazas: ${fav.cantidadSubrazas}",
                                    fontSize = 13.sp,
                                    color = mutedCol
                                )
                            }

                            IconButton(onClick = { viewModel.eliminarFavorito(fav.nombre) }) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Eliminar",
                                    tint = Color(0xFFEF4444)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
