package com.yuch.listanime.core.data.source.local

import com.yuch.listanime.core.data.source.local.entity.AnimeEntity
import com.yuch.listanime.core.data.source.local.room.AnimeDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val animeDao: AnimeDao) {
    // Obtenemos los animes
    fun getTopAnime(): Flow<List<AnimeEntity>> = animeDao.getTopAnime()
    // Obtenemos los animes favoritos
    fun getFavoriteAnime(): Flow<List<AnimeEntity>> {
        return animeDao.getFavoriteAnime()
    }
    // Insertamos los animes
    suspend fun insertAnime(animeList: List<AnimeEntity>) = animeDao.insertAnime(animeList)
    // Actualizamos el anime favorito
    fun setFavoriteAnime(anime: AnimeEntity, newState: Boolean) {
        anime.isFavorite = newState
        animeDao.updateFavoriteAnime(anime)
    }
}
