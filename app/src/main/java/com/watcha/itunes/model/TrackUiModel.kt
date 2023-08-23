package com.watcha.itunes.model

import com.watcha.domain.model.Track

data class TrackUiModel(
    val offset: Int = 0,
    val trackNumber: Int = 0,
    val trackName: String? = "",
    val collectionName: String? = "",
    val artistName: String? = "",
    val artwork: String? = "",
    var isFavorite: Int = 0,
    val position: Int = 0
)

fun Track.toUi(position: Int) = TrackUiModel(
    offset = this.offset,
    trackNumber = this.trackNumber,
    trackName = this.trackName,
    collectionName = this.collectionName,
    artistName = this.artistName,
    artwork = this.artwork,
    isFavorite = this.isFavorite,
    position = position
)
