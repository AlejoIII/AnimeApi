package com.yuch.listanime.core.domain.repository

import com.yuch.listanime.core.Resource
import com.yuch.listanime.core.domain.model.Anime
import kotlinx.coroutines.flow.Flow

interface IAnimeRepository {
    // Obtenemos los animes de la API
    fun getTopAnime(): Flow<Resource<List<Anime>>>
    fun getFavoriteAnime(): Flow<List<Anime>>
    fun setFavoriteAnime(anime: Anime, state: Boolean)
}