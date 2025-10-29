package com.example.artistexplorerapp.ui.model

data class TrackModel(
    val id: String,
    val albumId: String?,
    val title: String,
    val trackNumber: Int?,
    val duration: Int?
)
