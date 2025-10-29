package com.example.artistexplorerapp.data.container

import com.example.artistexplorerapp.data.repository.ArtistExplorerAppServerRepository
import com.example.artistexplorerapp.data.service.ArtistExplorerAppServerService
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ArtistExplorerAppServerContainer {
    companion object {
        val BASE_URL = "https://www.theaudiodb.com/api/v1/json/123/"
    }

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .baseUrl(BASE_URL)
        .build()

    private val artistExplorerAppServerService: ArtistExplorerAppServerService by lazy {
        retrofit.create(ArtistExplorerAppServerService::class.java)
    }

    val artistExplorerAppServerRepository: ArtistExplorerAppServerRepository by lazy {
        ArtistExplorerAppServerRepository(artistExplorerAppServerService)
    }
}