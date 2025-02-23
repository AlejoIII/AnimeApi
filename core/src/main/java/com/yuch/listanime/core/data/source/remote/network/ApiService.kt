package com.yuch.listanime.core.data.source.remote.network

import com.yuch.listanime.core.data.source.remote.response.ListAnimeResponse
import retrofit2.http.GET

interface ApiService {
    // Obtenemos los animes
    @GET ("top/anime")
    suspend fun getTopAnime(): ListAnimeResponse
}