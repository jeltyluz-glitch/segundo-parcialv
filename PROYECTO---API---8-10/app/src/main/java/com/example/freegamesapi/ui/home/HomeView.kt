package com.example.freegamesapi.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.freegamesapi.ui.cats.CatFactsScreen
import com.example.freegamesapi.ui.dogs.DogBreedsScreen
import com.example.freegamesapi.ui.dogs.DogDetailScreen
import com.example.freegamesapi.ui.dogs.DogViewModel
import com.example.freegamesapi.ui.favorites.FavoritesScreen
import com.example.freegamesapi.ui.weather.WeatherScreen

@Composable
fun HomeView() {
    var selectedTab by remember { mutableIntStateOf(0) }
    var selectedBreedDetail by remember { mutableStateOf<String?>(null) }
    val dogViewModel: DogViewModel = viewModel()

    val bgCol = Color(0xFF0F0F13)
    val primaryPurp = Color(0xFFC4B5FD)
    val textCol = Color.White
    val mutedCol = Color(0xFF9CA3AF)

    Scaffold(
        containerColor = bgCol,
        bottomBar = {
            NavigationBar(
                containerColor = bgCol,
                contentColor = mutedCol,
                tonalElevation = 0.dp
            ) {
                // Tab 0: Razas de Perros
                NavigationBarItem(
                    selected = selectedTab == 0 && selectedBreedDetail == null,
                    onClick = {
                        selectedTab = 0
                        selectedBreedDetail = null
                    },
                    icon = { Icon(Icons.Default.Pets, contentDescription = "Perros") },
                    label = { Text("Perros", fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = primaryPurp,
                        selectedTextColor = textCol,
                        unselectedIconColor = mutedCol,
                        unselectedTextColor = mutedCol,
                        indicatorColor = Color(0xFF3B3355)
                    )
                )

                // Tab 1: Clima La Paz
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = {
                        selectedTab = 1
                        selectedBreedDetail = null
                    },
                    icon = { Icon(Icons.Default.Cloud, contentDescription = "Clima") },
                    label = { Text("Clima", fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = primaryPurp,
                        selectedTextColor = textCol,
                        unselectedIconColor = mutedCol,
                        unselectedTextColor = mutedCol,
                        indicatorColor = Color(0xFF3B3355)
                    )
                )

                // Tab 2: Cat Facts
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = {
                        selectedTab = 2
                        selectedBreedDetail = null
                    },
                    icon = { Icon(Icons.Default.Quiz, contentDescription = "Gatos") },
                    label = { Text("Gatos", fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = primaryPurp,
                        selectedTextColor = textCol,
                        unselectedIconColor = mutedCol,
                        unselectedTextColor = mutedCol,
                        indicatorColor = Color(0xFF3B3355)
                    )
                )

                // Tab 3: Favoritos Room
                NavigationBarItem(
                    selected = selectedTab == 3,
                    onClick = {
                        selectedTab = 3
                        selectedBreedDetail = null
                    },
                    icon = { Icon(Icons.Default.Favorite, contentDescription = "Favoritos") },
                    label = { Text("Favoritos", fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = primaryPurp,
                        selectedTextColor = textCol,
                        unselectedIconColor = mutedCol,
                        unselectedTextColor = mutedCol,
                        indicatorColor = Color(0xFF3B3355)
                    )
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            val breedDetail = selectedBreedDetail
            if (breedDetail != null) {
                DogDetailScreen(
                    breedName = breedDetail,
                    viewModel = dogViewModel,
                    onBack = { selectedBreedDetail = null }
                )
            } else {
                when (selectedTab) {
                    0 -> DogBreedsScreen(
                        viewModel = dogViewModel,
                        onSelectBreed = { name -> selectedBreedDetail = name }
                    )
                    1 -> WeatherScreen()
                    2 -> CatFactsScreen()
                    3 -> FavoritesScreen(
                        onSelectBreed = { name ->
                            selectedTab = 0
                            selectedBreedDetail = name
                        }
                    )
                }
            }
        }
    }
}
