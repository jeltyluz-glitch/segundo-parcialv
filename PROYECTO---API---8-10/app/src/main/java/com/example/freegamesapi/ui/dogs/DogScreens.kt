package com.example.freegamesapi.ui.dogs

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
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
import com.example.freegamesapi.model.Breed

@Composable
fun DogBreedsScreen(
    viewModel: DogViewModel = viewModel(),
    onSelectBreed: (String) -> Unit
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
            text = "Razas de Perros y Subrazas",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = textCol
        )
        Spacer(modifier = Modifier.height(16.dp))

        when (val state = uiState) {
            is DogUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = primaryPurp)
                }
            }
            is DogUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = state.message, color = Color(0xFFFCA5A5))
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { viewModel.cargarRazas() }) {
                            Icon(Icons.Default.Refresh, contentDescription = null)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Reintentar")
                        }
                    }
                }
            }
            is DogUiState.Success -> {
                if (state.breeds.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No se encontraron razas", color = mutedCol, fontSize = 16.sp)
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(state.breeds) { breed ->
                            BreedItemCard(
                                breed = breed,
                                cardCol = cardCol,
                                borderCol = borderCol,
                                textCol = textCol,
                                mutedCol = mutedCol,
                                primaryPurp = primaryPurp,
                                onClick = { onSelectBreed(breed.nombre) },
                                onToggleFavorite = { viewModel.toggleFavorito(breed) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BreedItemCard(
    breed: Breed,
    cardCol: Color,
    borderCol: Color,
    textCol: Color,
    mutedCol: Color,
    primaryPurp: Color,
    onClick: () -> Unit,
    onToggleFavorite: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
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
                    text = breed.nombre.replaceFirstChar { it.uppercase() },
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = textCol
                )
                Spacer(modifier = Modifier.height(4.dp))
                if (breed.subrazas.isEmpty()) {
                    Text(
                        text = "Sin subrazas",
                        fontSize = 13.sp,
                        color = mutedCol
                    )
                } else {
                    Text(
                        text = "Subrazas: ${breed.subrazas.joinToString(", ") { it.replaceFirstChar { c -> c.uppercase() } }}",
                        fontSize = 13.sp,
                        color = primaryPurp
                    )
                }
            }

            IconButton(onClick = onToggleFavorite) {
                Icon(
                    imageVector = if (breed.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorito",
                    tint = if (breed.isFavorite) Color(0xFFEF4444) else mutedCol
                )
            }
        }
    }
}

@Composable
fun DogDetailScreen(
    breedName: String,
    viewModel: DogViewModel,
    onBack: () -> Unit
) {
    val breed = viewModel.getBreedByName(breedName)

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
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { onBack() }.padding(vertical = 8.dp)
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = primaryPurp)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Volver", color = primaryPurp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (breed == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Raza no encontrada", color = textCol, fontSize = 18.sp)
            }
        } else {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                color = cardCol,
                border = BorderStroke(1.dp, borderCol)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(
                        text = breed.nombre.replaceFirstChar { it.uppercase() },
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = textCol
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Cantidad de subrazas: ${breed.cantidadSubrazas}",
                        fontSize = 15.sp,
                        color = primaryPurp
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                    HorizontalDivider(color = borderCol)
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Lista de subrazas:",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = textCol
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    if (breed.subrazas.isEmpty()) {
                        Text(
                            text = "No existen subrazas registradas para esta raza.",
                            fontSize = 14.sp,
                            color = mutedCol
                        )
                    } else {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier.heightIn(max = 300.dp)
                        ) {
                            items(breed.subrazas) { sub ->
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = Color(0xFF22222D)
                                ) {
                                    Text(
                                        text = "• ${sub.replaceFirstChar { it.uppercase() }}",
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                        color = textCol,
                                        fontSize = 14.sp
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = { viewModel.toggleFavorito(breed) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (breed.isFavorite) Color(0xFF7F1D1D) else primaryPurp
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            imageVector = if (breed.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = null,
                            tint = if (breed.isFavorite) Color.White else Color(0xFF0F0F13)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (breed.isFavorite) "Quitar de favoritas" else "Marcar favorita",
                            color = if (breed.isFavorite) Color.White else Color(0xFF0F0F13),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
