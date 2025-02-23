package com.yuch.listanime.core.domain.usecase

import com.yuch.listanime.core.domain.model.Anime
import com.yuch.listanime.core.domain.repository.IAnimeRepository

class AnimeInteractor(private val animeRepository: IAnimeRepository): AnimeUseCase {
    // Obtenemos los animes de la API
    override fun getTopAnime() = animeRepository.getTopAnime()
    override fun getFavoriteAnime() = animeRepository.getFavoriteAnime()
    override fun setFavoriteAnime(anime: Anime, state: Boolean) = animeRepository.setFavoriteAnime(anime, state)
}