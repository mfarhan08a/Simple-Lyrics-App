package com.mfarhan08a.simplelyricsapp.core.data.source.remote

import android.util.Log
import com.mfarhan08a.simplelyricsapp.core.data.source.remote.network.ApiResponse
import com.mfarhan08a.simplelyricsapp.core.data.source.remote.network.ApiService
import com.mfarhan08a.simplelyricsapp.core.data.source.remote.response.Lyrics
import com.mfarhan08a.simplelyricsapp.core.data.source.remote.response.TrackListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getListTrack(): Flow<ApiResponse<List<TrackListItem>>> {
        return flow {
            try {
                val response = apiService.getListTrack()
                val data = response.message.body.trackList
                if (data.isNotEmpty()) {
                    emit(ApiResponse.Success(response.message.body.trackList))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getTrackLyric(trackId: Int): Flow<ApiResponse<Lyrics>> {
        return flow {
            try {
                val response = apiService.getTrackLyric(trackId)
                val data = response.message.body.lyrics
                if (data != null) {
                    emit(ApiResponse.Success(response.message.body.lyrics))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun searchTrack(query: String): Flow<ApiResponse<List<TrackListItem>>> {
        return flow {
            try {
                val response = apiService.searchTrack(query)
                val data = response.message.body.trackList
                if (data.isNotEmpty()) {
                    emit(ApiResponse.Success(response.message.body.trackList))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}