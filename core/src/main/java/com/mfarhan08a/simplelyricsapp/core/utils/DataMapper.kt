package com.mfarhan08a.simplelyricsapp.core.utils

import com.mfarhan08a.simplelyricsapp.core.data.source.local.entity.FavoriteTrackEntity
import com.mfarhan08a.simplelyricsapp.core.data.source.local.entity.TrackEntity
import com.mfarhan08a.simplelyricsapp.core.data.source.remote.response.Lyrics
import com.mfarhan08a.simplelyricsapp.core.data.source.remote.response.TrackListItem
import com.mfarhan08a.simplelyricsapp.core.domain.model.Lyric
import com.mfarhan08a.simplelyricsapp.core.domain.model.MusicGenreListItems
import com.mfarhan08a.simplelyricsapp.core.domain.model.MusicGenres
import com.mfarhan08a.simplelyricsapp.core.domain.model.Track

object DataMapper {
    fun mapResponsesToEntities(input: List<TrackListItem>): List<TrackEntity> {
        val trackEntities = mutableListOf<TrackEntity>()

        for (trackListItem in input) {
            val trackData = trackListItem.track

            val trackEntity = TrackEntity(
                trackId = trackData.trackId,
                commontrackId = trackData.commontrackId,
                trackName = trackData.trackName,
                primaryGenres = trackData.primaryGenres.musicGenreList.firstOrNull()?.musicGenre?.musicGenreName
                    ?: "",
                artistId = trackData.artistId,
                artistName = trackData.artistName,
                trackRating = trackData.trackRating,
                instrumental = trackData.instrumental,
                albumName = trackData.albumName,
                albumId = trackData.albumId,
                hasLyrics = trackData.hasLyrics,
                trackShareUrl = trackData.trackShareUrl,
                updatedTime = trackData.updatedTime
            )

            trackEntities.add(trackEntity)
        }

        return trackEntities
    }

    fun mapEntitiesToDomain(trackEntities: List<TrackEntity>): List<Track> {
        return trackEntities.map { trackEntity ->
            val primaryGenres = if (trackEntity.primaryGenres.isNotEmpty()) {
                listOf(MusicGenreListItems(MusicGenres(1, trackEntity.primaryGenres)))
            } else {
                emptyList()
            }

            Track(
                updatedTime = trackEntity.updatedTime,
                trackShareUrl = trackEntity.trackShareUrl,
                primaryGenres = primaryGenres,
                artistName = trackEntity.artistName,
                commontrackId = trackEntity.commontrackId,
                artistId = trackEntity.artistId,
                trackRating = trackEntity.trackRating,
                trackId = trackEntity.trackId,
                instrumental = trackEntity.instrumental,
                albumName = trackEntity.albumName,
                albumId = trackEntity.albumId,
                hasLyrics = trackEntity.hasLyrics,
                trackName = trackEntity.trackName
            )
        }
    }

    fun mapFavoriteEntitiesToDomain(trackEntities: List<FavoriteTrackEntity>): List<Track> {
        return trackEntities.map { trackEntity ->
            val primaryGenres = if (trackEntity.primaryGenres.isNotEmpty()) {
                listOf(MusicGenreListItems(MusicGenres(1, trackEntity.primaryGenres)))
            } else {
                emptyList()
            }

            Track(
                updatedTime = trackEntity.updatedTime,
                trackShareUrl = trackEntity.trackShareUrl,
                primaryGenres = primaryGenres,
                artistName = trackEntity.artistName,
                commontrackId = trackEntity.commontrackId,
                artistId = trackEntity.artistId,
                trackRating = trackEntity.trackRating,
                trackId = trackEntity.trackId,
                instrumental = trackEntity.instrumental,
                albumName = trackEntity.albumName,
                albumId = trackEntity.albumId,
                hasLyrics = trackEntity.hasLyrics,
                trackName = trackEntity.trackName
            )
        }
    }

    fun mapDomainToFavoriteEntity(track: Track): FavoriteTrackEntity {
        return FavoriteTrackEntity(
            trackId = track.trackId,
            commontrackId = track.commontrackId,
            trackName = track.trackName,
            primaryGenres = track.primaryGenres.firstOrNull()?.musicGenre?.musicGenreName ?: "",
            artistId = track.artistId,
            artistName = track.artistName,
            trackRating = track.trackRating,
            instrumental = track.instrumental,
            albumName = track.albumName,
            albumId = track.albumId,
            hasLyrics = track.hasLyrics,
            trackShareUrl = track.trackShareUrl,
            updatedTime = track.updatedTime
        )
    }

    fun mapLyricResponseToDomain(lyrics: Lyrics): Lyric {
        return Lyric(
            updatedTime = lyrics.updatedTime,
            lyricsBody = lyrics.lyricsBody,
            lyricsCopyright = lyrics.lyricsCopyright,
            lyricsId = lyrics.lyricsId,
        )
    }

}