package com.watcha.itunes.model

data class TrackUiModel(
    val trackNumber: Int = 0,
    val trackName: String = "",
    val collectionName: String = "",
    val artistName: String = "",
    val artwork: String = "",
    val isFavorite: Boolean = false
)
