package com.example.practica2_apis.data.remote


import com.example.practica2_apis.data.remote.Dto.DisneyResponseDto
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Path

interface DisneyApiService {
    @GET("character")
    suspend fun characters(@Query("page") page: Int): DisneyResponseDto
}