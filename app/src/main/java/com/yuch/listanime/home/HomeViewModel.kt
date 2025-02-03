package com.yuch.listanime.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.yuch.listanime.core.domain.usecase.AnimeUseCase

class HomeViewModel(animeUseCase: AnimeUseCase) : ViewModel() {
    // Obtenemos el anime
    val anime = animeUseCase.getTopAnime().asLiveData()
}