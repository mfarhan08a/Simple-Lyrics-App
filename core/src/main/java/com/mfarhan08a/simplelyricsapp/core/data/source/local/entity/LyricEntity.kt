package com.mfarhan08a.simplelyricsapp.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lyric")
data class LyricEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "lyricsId")
    val lyricsId: Int,

    @ColumnInfo(name = "lyricsBody")
    val lyricsBody: String,

    @ColumnInfo(name = "lyricsCopyright")
    val lyricsCopyright: String,

    @ColumnInfo(name = "updatedTime")
    val updatedTime: String,
)