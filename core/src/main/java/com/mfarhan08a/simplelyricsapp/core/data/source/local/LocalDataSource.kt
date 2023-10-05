package com.mfarhan08a.simplelyricsapp.core.data.source.local

import com.mfarhan08a.simplelyricsapp.core.data.source.local.entity.FavoriteTrackEntity
import com.mfarhan08a.simplelyricsapp.core.data.source.local.entity.TrackEntity
import com.mfarhan08a.simplelyricsapp.core.data.source.local.room.FavoriteTrackDao
import com.mfarhan08a.simplelyricsapp.core.data.source.local.room.TrackDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(
    private val trackDao: TrackDao,
    private val favoriteTrackDao: FavoriteTrackDao
) {

    //track
    fun getTrack(): Flow<List<TrackEntity>> = trackDao.getTrack()
    suspend fun insertTrack(trackList: List<TrackEntity>) = trackDao.insertTrack(trackList)

    //favorite_track
    fun setFavoriteTrack(track: FavoriteTrackEntity, saved: Boolean) {
        if (!saved) favoriteTrackDao.insertTrack(track) else favoriteTrackDao.delete(track)
    }
    fun getFavoriteTrack(): Flow<List<FavoriteTrackEntity>> = favoriteTrackDao.getFavoriteTrack()
    suspend fun isFavorite(track: FavoriteTrackEntity): Boolean = favoriteTrackDao.isExist(track.trackId)
}