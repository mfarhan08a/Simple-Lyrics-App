package com.mfarhan08a.simplelyricsapp.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TrackResponse(

	@field:SerializedName("message")
	val message: MessageTrack
)

data class BodyTrack(

	@field:SerializedName("track_list")
	val trackList: List<TrackListItem>
)

data class Tracks(

	@field:SerializedName("updated_time")
	val updatedTime: String,

	@field:SerializedName("track_share_url")
	val trackShareUrl: String,

	@field:SerializedName("primary_genres")
	val primaryGenres: PrimaryGenres,

	@field:SerializedName("artist_name")
	val artistName: String,

	@field:SerializedName("commontrack_id")
	val commontrackId: Int,

	@field:SerializedName("artist_id")
	val artistId: Int,

	@field:SerializedName("track_rating")
	val trackRating: Int,

	@field:SerializedName("track_id")
	val trackId: Int,

	@field:SerializedName("instrumental")
	val instrumental: Int,

	@field:SerializedName("album_name")
	val albumName: String,

	@field:SerializedName("album_id")
	val albumId: Int,

	@field:SerializedName("has_lyrics")
	val hasLyrics: Int,

	@field:SerializedName("track_name")
	val trackName: String
)

data class PrimaryGenres(

	@field:SerializedName("music_genre_list")
	val musicGenreList: List<MusicGenreListItem>
)

data class TrackListItem(

	@field:SerializedName("track")
	val track: Tracks
)

data class MessageTrack(

	@field:SerializedName("header")
	val header: HeaderTrack,

	@field:SerializedName("body")
	val body: BodyTrack
)

data class MusicGenre(

	@field:SerializedName("music_genre_id")
	val musicGenreId: Int,

	@field:SerializedName("music_genre_name")
	val musicGenreName: String
)

data class HeaderTrack(

	@field:SerializedName("status_code")
	val statusCode: Int,

	@field:SerializedName("execute_time")
	val executeTime: Double
)

data class MusicGenreListItem(
	@field:SerializedName("music_genre")
	val musicGenre: MusicGenre
)
