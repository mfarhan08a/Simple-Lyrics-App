package com.mfarhan08a.simplelyricsapp.ui.detail

import androidx.lifecycle.*
import com.mfarhan08a.simplelyricsapp.core.domain.model.Track
import com.mfarhan08a.simplelyricsapp.core.domain.usecase.TrackUseCase

class DetailViewModel(private val trackUseCase: TrackUseCase) : ViewModel() {
    private val _trackItem = MutableLiveData<Track>()
    val trackItem: LiveData<Track> = _trackItem
    var isFavorite: LiveData<Boolean> = _trackItem.switchMap { track ->
        trackUseCase.isFavorite(track).asLiveData()
    }

    fun setSelectedTrack(track: Track) {
        _trackItem.postValue(track)
    }

    fun setFavoriteTrack(saved: Boolean) {
        _trackItem.value?.let {
            trackUseCase.setFavoriteTrack(it, saved)
            _trackItem.postValue(it)
        }
    }

    fun getTrackLyric(trackId: Int) = trackUseCase.getTrackLyric(trackId).asLiveData()
}