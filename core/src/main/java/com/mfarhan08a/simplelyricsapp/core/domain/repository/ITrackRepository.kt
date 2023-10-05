package com.mfarhan08a.simplelyricsapp.core.domain.repository

import com.mfarhan08a.simplelyricsapp.core.data.Resource
import com.mfarhan08a.simplelyricsapp.core.domain.model.Lyric
import com.mfarhan08a.simplelyricsapp.core.domain.model.Track
import kotlinx.coroutines.flow.Flow

interface ITrackRepository {
    fun getListTrack(): Flow<Resource<List<Track>>>
    fun getTrackLyric(trackId: Int) : Flow<Resource<Lyric>>
    fun getFavoriteTrack(): Flow<List<Track>>
    fun setFavoriteTrack(track: Track, saved: Boolean)
    fun isFavorite(track: Track): Flow<Boolean>
    fun searchTrack(query: String): Flow<Resource<List<Track>>>
}