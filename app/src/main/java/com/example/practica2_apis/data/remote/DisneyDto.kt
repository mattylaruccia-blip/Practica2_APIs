package com.example.practica2_apis.data.remote

import com.google.gson.annotations.SerializedName

data class DisneyResponseDto(
    @SerializedName("data")
    val data: List<DisneyCharacterDto>? = null,
    @SerializedName("info")
    val info: DisneyInfoDto? = null
)

data class DisneyCharacterDto(
    @SerializedName("_id")
    val id: Int? = null,
    @SerializedName("allies")
    val allies: List<String>? = null,
    @SerializedName("enemies")
    val enemies: List<String>? = null,
    @SerializedName("films")
    val films: List<String>? = null,
    @SerializedName("imageUrl")
    val imageUrl: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("parkAttractions")
    val parkAttractions: List<String>? = null,
    @SerializedName("shortFilms")
    val shortFilms: List<String>? = null,
    @SerializedName("tvShows")
    val tvShows: List<String>? = null,
    @SerializedName("url")
    val url: String? = null,
    @SerializedName("videoGames")
    val videoGames: List<String>? = null
)

data class DisneyInfoDto(
    @SerializedName("count")
    val count: Int? = null,
    @SerializedName("nextPage")
    val nextPage: String? = null,
    @SerializedName("previousPage")
    val previousPage: String? = null,
    @SerializedName("totalPages")
    val totalPages: Int? = null
)

//fun DisneyItemDto.aResumen(): DisneyResumen {
//    val id = url.trimEnd('/').substringAfterLast('/').toInt()
//    return DisneyResumen(id = id, nombre = name, imagenUrl = "...")
//}

