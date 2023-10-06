@file:Suppress("DEPRECATED_ANNOTATION")

package com.mfarhan08a.simplelyricsapp.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Track(
    val updatedTime: String,
    val trackShareUrl: String,
    val primaryGenres: List<MusicGenreListItems>,
    val artistName: String,
    val commontrackId: Int,
    val artistId: Int,
    val trackRating: Int,
    val trackId: Int,
    val instrumental: Int,
    val albumName: String,
    val albumId: Int,
    val hasLyrics: Int,
    val trackName: String
) : Parcelable

@Parcelize
data class MusicGenreListItems(
    val musicGenre: MusicGenres
) : Parcelable

@Parcelize
data class MusicGenres(
    val musicGenreId: Int,
    val musicGenreName: String
) : Parcelable