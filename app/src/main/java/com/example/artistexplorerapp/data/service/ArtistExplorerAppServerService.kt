package com.example.artistexplorerapp.data.service

import com.example.artistexplorerapp.data.dto.AlbumSearchResponse
import com.example.artistexplorerapp.data.dto.ArtistSearchResponse
import com.example.artistexplorerapp.data.dto.TrackAlbumResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ArtistExplorerAppServerService {
    @GET("search.php")
    suspend fun searchArtist(
        @Query("s") artistName: String
    ): Response<ArtistSearchResponse>

    @GET("searchalbum.php")
    suspend fun searchAlbum(
        @Query("s") artistName: String
    ): Response<AlbumSearchResponse>

    @GET("track.php")
    suspend fun trackAlbum(
        @Query("m") albumId: String
    ): Response<TrackAlbumResponse>
}