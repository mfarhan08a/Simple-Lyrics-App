package com.mfarhan08a.simplelyricsapp.favorites.di

import com.mfarhan08a.simplelyricsapp.favorites.ui.FavoritesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoritesModule = module {
    viewModel { FavoritesViewModel(get()) }
}