package com.example.artistexplorerapp.data.repository

import com.example.artistexplorerapp.data.service.ArtistExplorerAppServerService
import com.example.artistexplorerapp.ui.model.AlbumModel
import com.example.artistexplorerapp.ui.model.ArtistModel
import com.example.artistexplorerapp.ui.model.TrackModel

class ArtistExplorerAppServerRepository (private val artistExplorerAppServerService: ArtistExplorerAppServerService){
    suspend fun searchArtist(name: String): Result<ArtistModel?> {
        return try {
            val response = artistExplorerAppServerService.searchArtist(name)
            if (!response.isSuccessful) {
                return Result.failure(Exception("HTTP ${response.code()}: ${response.message()}"))
            }

            val artist = response.body()?.artists?.firstOrNull()
            val artistModel = artist?.let {
                ArtistModel(
                    id = it.idArtist,
                    name = it.strArtist,
                    genre = it.strGenre,
                    biography = it.strBiographyEN,
                    imageURL = it.strArtistThumb
                )
            }
            Result.success(artistModel)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getAlbumTracks(albumId: String): Result<List<TrackModel>> {
        return try {
            val response = artistExplorerAppServerService.trackAlbum(albumId)
            if (!response.isSuccessful) {
                return Result.failure(Exception("HTTP ${response.code()}: ${response.message()}"))
            }

            val tracks = response.body()?.track ?: emptyList()
            val trackModels = tracks.map { track ->
                TrackModel(
                    id = track.idTrack,
                    albumId = track.idAlbum,
                    title = track.strTrack,
                    trackNumber = track.intTrackNumber?.toIntOrNull(),
                    duration = track.intDuration?.toIntOrNull()
                )
            }
            Result.success(trackModels)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun searchAlbums(artistName: String): Result<List<AlbumModel>> {
        return try {
            val response = artistExplorerAppServerService.searchAlbum(artistName)
            if (!response.isSuccessful) {
                return Result.failure(Exception("HTTP ${response.code()}: ${response.message()}"))
            }

            val body = response.body()
            if (body == null) {
                return Result.failure(Exception("Empty response body"))
            }

            val albums = body.album ?: emptyList()
            if (albums.isEmpty()) {
                return Result.failure(Exception("No albums found for artist: $artistName"))
            }

            val albumModels = albums.map { album ->
                AlbumModel(
                    id = album.idAlbum,
                    artistId = album.idArtist,
                    title = album.strAlbum,
                    artist = album.strArtist,
                    yearReleased = album.intYearReleased,
                    genre = album.strGenre ?: "Unknown",  // Provide default for null
                    imageURL = album.strAlbumThumb,
                    description = album.strDescriptionEN ?: ""
                )
            }
            Result.success(albumModels)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}