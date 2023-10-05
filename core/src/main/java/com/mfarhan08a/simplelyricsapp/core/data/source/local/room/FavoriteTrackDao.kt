package com.mfarhan08a.simplelyricsapp.core.data.source.local.room

import androidx.room.*
import com.mfarhan08a.simplelyricsapp.core.data.source.local.entity.FavoriteTrackEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteTrackDao {
    @Query("select * from favorite_track")
    fun getFavoriteTrack(): Flow<List<FavoriteTrackEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTrack(data: FavoriteTrackEntity)

    @Delete
    fun delete(data: FavoriteTrackEntity)

    @Query("SELECT EXISTS (SELECT 1 FROM favorite_track WHERE trackId = :trackId)")
    suspend fun isExist(trackId: Int): Boolean
}