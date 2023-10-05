package com.mfarhan08a.simplelyricsapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mfarhan08a.simplelyricsapp.core.domain.usecase.TrackUseCase
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

class HomeViewModel(trackUseCase: TrackUseCase) : ViewModel() {
    val track = trackUseCase.getListTrack().asLiveData()

    val queryChannel = BroadcastChannel<String>(Channel.CONFLATED)
    @Suppress("DEPRECATION")
    @OptIn(ObsoleteCoroutinesApi::class)
    val search = queryChannel.asFlow()
        .debounce(1000)
        .distinctUntilChanged()
        .filter {
            it.trim().isNotEmpty()
        }
        .mapLatest {
            trackUseCase.searchTrack(it)
        }
        .asLiveData()
}