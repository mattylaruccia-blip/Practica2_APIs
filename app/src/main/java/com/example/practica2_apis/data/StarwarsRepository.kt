package com.example.practica2_apis.data

import com.example.practica2_apis.data.mapper.toDomain
import com.example.practica2_apis.data.modelStarwars.StarwarsCharacter
import com.example.practica2_apis.data.remote.RetrofitClient
import com.example.practica2_apis.data.remote.StarwarsApiService

class StarwarsRepository(private val api: StarwarsApiService) {
    suspend fun characters(page: Int): StarwarsCharacter = api.characters(page).toDomain()

    companion object {
        val instance by lazy {
            StarwarsRepository(RetrofitClient.starwarsApi)
        }
    }
}
