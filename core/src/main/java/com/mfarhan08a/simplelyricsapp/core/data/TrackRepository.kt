package com.mfarhan08a.simplelyricsapp.core.data

import com.mfarhan08a.simplelyricsapp.core.data.source.local.LocalDataSource
import com.mfarhan08a.simplelyricsapp.core.data.source.remote.RemoteDataSource
import com.mfarhan08a.simplelyricsapp.core.data.source.remote.network.ApiResponse
import com.mfarhan08a.simplelyricsapp.core.data.source.remote.response.TrackListItem
import com.mfarhan08a.simplelyricsapp.core.domain.model.Lyric
import com.mfarhan08a.simplelyricsapp.core.domain.model.Track
import com.mfarhan08a.simplelyricsapp.core.domain.repository.ITrackRepository
import com.mfarhan08a.simplelyricsapp.core.utils.AppExecutors
import com.mfarhan08a.simplelyricsapp.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class TrackRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors,
) : ITrackRepository {
    override fun getListTrack(): Flow<Resource<List<Track>>> =
        object : NetworkBoundResource<List<Track>, List<TrackListItem>>() {
            override fun loadFromDB(): Flow<List<Track>> {
                return localDataSource.getTrack().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override suspend fun createCall(): Flow<ApiResponse<List<TrackListItem>>> =
                remoteDataSource.getListTrack()


            override suspend fun saveCallResult(data: List<TrackListItem>) {
                val trackList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertTrack(trackList)
            }

            override fun shouldFetch(data: List<Track>?): Boolean =
                data == null || data.isEmpty()

        }.asFlow()

    override fun getTrackLyric(trackId: Int): Flow<Resource<Lyric>> = flow {
        emit(Resource.Loading())
        when (val apiResponse = remoteDataSource.getTrackLyric(trackId).first()) {
            is ApiResponse.Success -> {
                val response = apiResponse.data
                emit(Resource.Success(DataMapper.mapLyricResponseToDomain(response)))
            }
            is ApiResponse.Empty -> {
                emit(Resource.Error("There is no lyric found"))
            }
            is ApiResponse.Error -> {
                emit(Resource.Error(apiResponse.errorMessage))
            }
        }
    }

    override fun getFavoriteTrack(): Flow<List<Track>> {
        return localDataSource.getFavoriteTrack().map {
            DataMapper.mapFavoriteEntitiesToDomain(it)
        }
    }

    override fun setFavoriteTrack(track: Track, saved: Boolean) {
        val trackEntity = DataMapper.mapDomainToFavoriteEntity(track)
        appExecutors.diskIO().execute { localDataSource.setFavoriteTrack(trackEntity, saved) }
    }

    override fun isFavorite(track: Track): Flow<Boolean> {
        val trackEntity = DataMapper.mapDomainToFavoriteEntity(track)
        return flow {
            emit(localDataSource.isFavorite(trackEntity))
        }
    }

    override fun searchTrack(query: String): Flow<Resource<List<Track>>> = flow {
        emit(Resource.Loading())
        when (val apiResponse = remoteDataSource.searchTrack(query).first()) {
            is ApiResponse.Success -> {
                val response = DataMapper.mapResponsesToEntities(apiResponse.data)
                emit(Resource.Success(DataMapper.mapEntitiesToDomain(response)))
            }
            is ApiResponse.Empty -> {
                emit(Resource.Error("No Tracks found"))
            }
            is ApiResponse.Error -> {
                emit(Resource.Error(apiResponse.errorMessage))
            }
        }
    }


}