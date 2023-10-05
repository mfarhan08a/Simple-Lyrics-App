package com.mfarhan08a.simplelyricsapp.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_track")
data class FavoriteTrackEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "trackId")
    val trackId: Int,

    @ColumnInfo(name = "commontrackId")
    val commontrackId: Int,

    @ColumnInfo(name = "trackName")
    val trackName: String,

    @ColumnInfo(name = "primaryGenres")
    val primaryGenres: String,

    @ColumnInfo(name = "artistId")
    val artistId: Int,

    @ColumnInfo(name = "artistName")
    val artistName: String,

    @ColumnInfo(name = "trackRating")
    val trackRating: Int,

    @ColumnInfo(name = "instrumental")
    val instrumental: Int,

    @ColumnInfo(name = "albumName")
    val albumName: String,

    @ColumnInfo(name = "albumId")
    val albumId: Int,

    @ColumnInfo(name = "hasLyrics")
    val hasLyrics: Int,

    @ColumnInfo(name = "trackShareUrl")
    val trackShareUrl: String,

    @ColumnInfo(name = "updatedTime")
    val updatedTime: String,
)
