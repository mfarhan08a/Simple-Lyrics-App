package com.mfarhan08a.simplelyricsapp.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mfarhan08a.simplelyricsapp.core.data.source.local.entity.FavoriteTrackEntity
import com.mfarhan08a.simplelyricsapp.core.data.source.local.entity.TrackEntity

@Database(
    entities = [TrackEntity::class, FavoriteTrackEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TrackLyricDatabase : RoomDatabase() {
    abstract fun trackDao(): TrackDao
    abstract fun favoriteTrackDao(): FavoriteTrackDao
}