package com.example.practica2_apis.data.mapper

import com.example.practica2_apis.data.modelStarwars.Result
import com.example.practica2_apis.data.modelStarwars.StarwarsCharacter
import com.example.practica2_apis.data.remote.Dto.StarwarsCharacterDto
import com.example.practica2_apis.data.remote.Dto.StarwarsResponseDto

fun StarwarsResponseDto.toDomain(): StarwarsCharacter = StarwarsCharacter(
    count = count ?: 0,
    next = next?.takeIf { it.isNotBlank() },
    previous = previous?.takeIf { it.isNotBlank() },
    results = results.orEmpty().map { it.toDomain() }
)

fun StarwarsCharacterDto.toDomain(): Result = Result(
    birth_year = birth_year.orEmpty(),
    created = created.orEmpty(),
    edited = edited.orEmpty(),
    eye_color = eye_color.orEmpty(),
    films = films.orEmpty(),
    gender = gender.orEmpty(),
    hair_color = hair_color.orEmpty(),
    height = height.orEmpty(),
    homeworld = homeworld.orEmpty(),
    mass = mass.orEmpty(),
    name = name?.takeIf { it.isNotBlank() } ?: "Sin nombre",
    skin_color = skin_color.orEmpty(),
    species = species.orEmpty(),
    starships = starships.orEmpty(),
    url = url.orEmpty(),
    vehicles = vehicles.orEmpty()
)
