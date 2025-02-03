package com.yuch.listanime.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListAnimeResponse(
    // Atributos de la clase ListAnimeResponse
    @field:SerializedName("pagination")
	val pagination: Pagination? = null,
    // Atributo data de tipo lista de AnimeResponse
    @field:SerializedName("data")
	val data: List<AnimeResponse?>? = null
)

