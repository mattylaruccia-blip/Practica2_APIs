package com.example.practica2_apis.data.remote

import com.example.practica2_apis.data.remote.Dto.StarwarsResponseDto
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Path

interface StarwarsApiService {
    @GET("people/")
    suspend fun characters(
        @Query("page") page: Int,
        @Query("format") format: String = "json"
    ): StarwarsResponseDto
}