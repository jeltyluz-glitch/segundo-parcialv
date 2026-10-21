package com.example.freegamesapi.data.remote

import com.example.freegamesapi.data.remote.api.CatFactApiService
import com.example.freegamesapi.data.remote.api.DogCeoApiService
import com.example.freegamesapi.data.remote.api.OpenMeteoApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/** Única instancia de Retrofit de la app (patrón singleton con `object`). */
object RetrofitClient {

    // Debe terminar en "/" para que Retrofit pueda concatenar las rutas relativas.
    private const val BASE_URL_OPEN_METEO = "https://api.open-meteo.com/v1/"
    private const val BASE_URL_DOG_CEO = "https://dog.ceo/api/"
    private const val BASE_URL_CAT_FACT = "https://catfact.ninja/"

    private val clienteHttp: OkHttpClient = OkHttpClient.Builder()
        // Muestra cada petición en Logcat (filtrar por "OkHttp"): útil para depurar y para clase.
        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC })
        .build()

    // API 1: Open-Meteo
    val openMeteoApi: OpenMeteoApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_OPEN_METEO)
            .client(clienteHttp)
            .addConverterFactory(GsonConverterFactory.create()) // JSON <-> objetos Kotlin
            .build()
            .create(OpenMeteoApiService::class.java)
    }

    // API 2: Dog CEO
    val dogCeoApi: DogCeoApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_DOG_CEO)
            .client(clienteHttp)
            .addConverterFactory(GsonConverterFactory.create()) // JSON <-> objetos Kotlin
            .build()
            .create(DogCeoApiService::class.java)
    }

    // API 3: Cat Facts
    val catFactApi: CatFactApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_CAT_FACT)
            .client(clienteHttp)
            .addConverterFactory(GsonConverterFactory.create()) // JSON <-> objetos Kotlin
            .build()
            .create(CatFactApiService::class.java)
    }
}
