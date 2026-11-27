package com.example.practica2_apis.data.remote


import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/** Cliente centralizado de Retrofit con interceptor de logs HTTP para OkHttp. */
object RetrofitClient {

    private const val DISNEY_BASE_URL = "https://api.disneyapi.dev/"
    private const val STARWARS_BASE_URL = "https://swapi.dev/api/"


    private val clienteHttp: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC })
        .build()

    val disneyApi: DisneyApiService by lazy {
        service(DISNEY_BASE_URL, DisneyApiService::class.java)
    }

    val starwarsApi: StarwarsApiService by lazy {
        service(STARWARS_BASE_URL, StarwarsApiService::class.java)
    }
    private fun <T> service(baseUrl: String, type: Class<T>): T =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(clienteHttp)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(type)
}
