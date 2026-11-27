package com.example.practica2_apis.data.modelStarwars

data class StarwarsCharacter(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Result>
)