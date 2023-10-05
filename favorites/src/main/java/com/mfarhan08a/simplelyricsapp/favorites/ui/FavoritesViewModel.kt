package com.mfarhan08a.simplelyricsapp.favorites.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mfarhan08a.simplelyricsapp.core.domain.usecase.TrackUseCase

class FavoritesViewModel(trackUseCase: TrackUseCase) : ViewModel() {
    val favoritesTrack = trackUseCase.getFavoriteTrack().asLiveData()
}