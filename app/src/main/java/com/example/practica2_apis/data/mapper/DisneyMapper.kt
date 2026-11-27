package com.example.practica2_apis.data.mapper

import com.example.practica2_apis.data.remote.Dto.DisneyCharacterDto
import com.example.practica2_apis.data.remote.Dto.DisneyInfoDto
import com.example.practica2_apis.data.remote.Dto.DisneyResponseDto
import com.example.practica2_apis.modelDisney.Data
import com.example.practica2_apis.modelDisney.DisneyCharacter
import com.example.practica2_apis.modelDisney.Info


fun DisneyResponseDto.toDomain(): DisneyCharacter = DisneyCharacter(
    data = data.orEmpty().map { it.toDomain() },
    info = info?.toDomain() ?: Info(count = 0, nextPage = null, previousPage = null, totalPages = 1)
)

fun DisneyCharacterDto.toDomain(): Data = Data(
    _id = id ?: 0,
    allies = allies.orEmpty(),
    enemies = enemies.orEmpty(),
    films = films.orEmpty(),
    imageUrl = imageUrl?.takeIf { it.isNotBlank() },
    name = name?.takeIf { it.isNotBlank() } ?: "Sin nombre",
    parkAttractions = parkAttractions.orEmpty(),
    shortFilms = shortFilms.orEmpty(),
    tvShows = tvShows.orEmpty(),
    url = url.orEmpty(),
    videoGames = videoGames.orEmpty()
)

fun DisneyInfoDto.toDomain(): Info = Info(
    count = count ?: 0,
    nextPage = nextPage,
    previousPage = previousPage,
    totalPages = (totalPages ?: 1).coerceAtLeast(1)
)
