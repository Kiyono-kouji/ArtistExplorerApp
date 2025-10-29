package com.example.artistexplorerapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.artistexplorerapp.data.container.ArtistExplorerAppServerContainer
import com.example.artistexplorerapp.data.repository.ArtistExplorerAppServerRepository
import com.example.artistexplorerapp.ui.model.AlbumModel
import com.example.artistexplorerapp.ui.model.ArtistModel
import com.example.artistexplorerapp.ui.model.TrackModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ArtistExplorerAppViewModel(
    private val repository: ArtistExplorerAppServerRepository = ArtistExplorerAppServerContainer().artistExplorerAppServerRepository
) : ViewModel() {

    private val _artistInfo = MutableStateFlow<ArtistModel?>(null)
    val artistInfo: StateFlow<ArtistModel?> = _artistInfo.asStateFlow()

    private val _albums = MutableStateFlow<List<AlbumModel>>(emptyList())
    val albums: StateFlow<List<AlbumModel>> = _albums.asStateFlow()

    private val _tracks = MutableStateFlow<List<TrackModel>>(emptyList())
    val tracks: StateFlow<List<TrackModel>> = _tracks.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun searchArtist(artistName: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            println("Searching artist: $artistName")

            repository.searchArtist(artistName)
                .onSuccess { artist ->
                    println("Artist found: ${artist?.name}")
                    _artistInfo.value = artist
                    artist?.name?.let { name ->
                        println("Fetching albums for artist: $name")
                        fetchAlbums(name)
                    }
                }
                .onFailure { exception ->
                    println("Failed to search artist: ${exception.message}")
                    _errorMessage.value = exception.message ?: "Failed to search artist"
                }

            _isLoading.value = false
        }
    }

    fun fetchAlbums(artistName: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            println("Fetching albums for: $artistName")

            repository.searchAlbums(artistName)
                .onSuccess { albumList ->
                    println("Albums fetched successfully: ${albumList.size} albums")
                    _albums.value = albumList
                }
                .onFailure { exception ->
                    println("Failed to fetch albums: ${exception.message}")
                    _errorMessage.value = exception.message ?: "Failed to fetch albums"
                }

            _isLoading.value = false
        }
    }

    fun fetchAlbumTracks(albumId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            repository.getAlbumTracks(albumId)
                .onSuccess { trackList ->
                    _tracks.value = trackList
                }
                .onFailure { exception ->
                    _errorMessage.value = exception.message ?: "Failed to fetch tracks"
                }

            _isLoading.value = false
        }
    }

    init {
        searchArtist("Two Door Cinema Club")
    }
}