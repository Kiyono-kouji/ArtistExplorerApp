package com.example.artistexplorerapp.ui.model

data class AlbumModel(
    val id: String,
    val artistId: String,
    val title: String,
    val artist: String,
    val yearReleased: String,
    val genre: String?,
    val imageURL: String?,
    val description: String?
)