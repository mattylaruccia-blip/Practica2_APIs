package com.example.practica2_apis.data.remote.Dto

import com.google.gson.annotations.SerializedName

data class StarwarsResponseDto(
    @SerializedName("count")
    val count: Int? = null,
    @SerializedName("next")
    val next: String? = null,
    @SerializedName("previous")
    val previous: String? = null,
    @SerializedName("results")
    val results: List<StarwarsCharacterDto>? = null
)

data class StarwarsCharacterDto(
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("height")
    val height: String? = null,
    @SerializedName("mass")
    val mass: String? = null,
    @SerializedName("hair_color")
    val hair_color: String? = null,
    @SerializedName("skin_color")
    val skin_color: String? = null,
    @SerializedName("eye_color")
    val eye_color: String? = null,
    @SerializedName("birth_year")
    val birth_year: String? = null,
    @SerializedName("gender")
    val gender: String? = null,
    @SerializedName("homeworld")
    val homeworld: String? = null,
    @SerializedName("films")
    val films: List<String>? = null,
    @SerializedName("species")
    val species: List<String>? = null,
    @SerializedName("vehicles")
    val vehicles: List<String>? = null,
    @SerializedName("starships")
    val starships: List<String>? = null,
    @SerializedName("created")
    val created: String? = null,
    @SerializedName("edited")
    val edited: String? = null,
    @SerializedName("url")
    val url: String? = null
)

//fun StarwarsItemDto.aResumen(): StarwarsResumen {
//    val id = url.trimEnd('/').substringAfterLast('/').toInt()
//    return StarwarsResumen(id = id, nombre = name, imagenUrl = "...")
//}

