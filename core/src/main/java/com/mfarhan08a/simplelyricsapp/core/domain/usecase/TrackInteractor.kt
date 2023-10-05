package com.mfarhan08a.simplelyricsapp.core.domain.usecase

import com.mfarhan08a.simplelyricsapp.core.data.Resource
import com.mfarhan08a.simplelyricsapp.core.domain.model.Track
import com.mfarhan08a.simplelyricsapp.core.domain.repository.ITrackRepository
import kotlinx.coroutines.flow.Flow

class TrackInteractor(private val trackRepository: ITrackRepository) : TrackUseCase {
    override fun getListTrack() = trackRepository.getListTrack()
    override fun getTrackLyric(trackId: Int) = trackRepository.getTrackLyric(trackId)

    override fun getFavoriteTrack() =
        trackRepository.getFavoriteTrack()

    override fun setFavoriteTrack(track: Track, saved: Boolean) =
        trackRepository.setFavoriteTrack(track, saved)

    override fun isFavorite(track: Track): Flow<Boolean> = trackRepository.isFavorite(track)
    override fun searchTrack(query: String): Flow<Resource<List<Track>>> = trackRepository.searchTrack(query)
}