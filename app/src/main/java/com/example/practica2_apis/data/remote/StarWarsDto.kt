package com.example.practica2_apis.data.remote

import com.google.gson.annotations.SerializedName

data class StarwarsResponseDto(
    val count: Int,
    val next: String?, // puede ser null: por eso lleva "?"
    val results: List<StarwarsCharacterDto>
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
    @SerializedName("vehicule")
    val vehicule: List<String>? = null,
    @SerializedName("starship")
    val starship: List<String>? = null,
    @SerializedName("create")
    val created: String? = null,
    @SerializedName("edited")
    val edited: String? = null,
    @SerializedName("url")
    val url: String? = null
)

data class StarwarsInfoDto(
    @SerializedName("count")
    val count: Int? = null,
    @SerializedName("nextPage")
    val nextPage: String? = null,
    @SerializedName("previousPage")
    val previousPage: String? = null,
    @SerializedName("totalPages")
    val totalPages: Int? = null
)

//fun StarwarsItemDto.aResumen(): StarwarsResumen {
//    val id = url.trimEnd('/').substringAfterLast('/').toInt()
//    return StarwarsResumen(id = id, nombre = name, imagenUrl = "...")
//}

