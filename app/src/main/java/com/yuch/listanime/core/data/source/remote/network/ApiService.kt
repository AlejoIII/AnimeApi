package com.yuch.listanime.core.data.source.remote.network

import com.yuch.listanime.core.data.source.remote.response.ListAnimeResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET ("top/anime")
    fun getTopAnime(): Call<ListAnimeResponse>
}