package com.example.practica2_apis.data


import com.example.practica2_apis.data.mapper.toDomain
import com.example.practica2_apis.data.remote.DisneyApiService
import com.example.practica2_apis.data.remote.RetrofitClient
import com.example.practica2_apis.modelDisney.DisneyCharacter

class DisneyRepository(private val api: DisneyApiService) {
    suspend fun characters(page: Int): DisneyCharacter = api.characters(page).toDomain()

    companion object {
        val instance by lazy {
            DisneyRepository(RetrofitClient.disneyApi)
        }
    }
}
