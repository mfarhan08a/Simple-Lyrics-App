package com.mfarhan08a.simplelyricsapp.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Lyric(
    val updatedTime: String,
    val lyricsBody: String,
    val lyricsCopyright: String,
    val lyricsId: Int
): Parcelable
