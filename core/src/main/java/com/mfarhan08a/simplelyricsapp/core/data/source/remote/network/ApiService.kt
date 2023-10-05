package com.mfarhan08a.simplelyricsapp.core.data.source.remote.network

import com.mfarhan08a.simplelyricsapp.core.data.source.remote.response.LyricResponse
import com.mfarhan08a.simplelyricsapp.core.data.source.remote.response.TrackResponse
import com.mfarhan08a.simplelyricsapp.core.di.api_key
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("chart.tracks.get")
    suspend fun getListTrack(
        @Query("page_size") pageSize: Int? = 20,
        @Query("s_track_rating") sortByRating: String? = "desc",
        @Query("apikey") apikey: String? = api_key
    ): TrackResponse

    @GET("track.lyrics.get")
    suspend fun getTrackLyric(
        @Query("track_id") trackId: Int?,
        @Query("apikey") apikey: String? = api_key
    ) : LyricResponse

    @GET("track.search")
    suspend fun searchTrack(
        @Query("q") query: String?,
        @Query("page_size") pageSize: Int? = 50,
        @Query("s_track_rating") sortByRating: String? = "desc",
        @Query("apikey") apikey: String? = api_key
    ) : TrackResponse
}