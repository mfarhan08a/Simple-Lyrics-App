package com.mfarhan08a.simplelyricsapp.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class LyricResponse(
    @field:SerializedName("message")
    val message: MessageLyric
)

data class Lyrics(
    @field:SerializedName("updated_time")
    val updatedTime: String,

    @field:SerializedName("lyrics_body")
    val lyricsBody: String,

    @field:SerializedName("lyrics_copyright")
    val lyricsCopyright: String,

    @field:SerializedName("lyrics_id")
    val lyricsId: Int
)

data class BodyLyric(
    @field:SerializedName("lyrics")
    val lyrics: Lyrics
)

data class MessageLyric(
    @field:SerializedName("header")
    val header: HeaderLyric,

    @field:SerializedName("body")
    val body: BodyLyric
)

data class HeaderLyric(

    @field:SerializedName("status_code")
    val statusCode: Int,

    @field:SerializedName("execute_time")
    val executeTime: Double
)
