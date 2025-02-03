package com.yuch.listanime.favorite

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteModule = module {
    // Inicializamos el ViewModel de favoritos
    viewModel { FavoriteViewModel(get()) }
}