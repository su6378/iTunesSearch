package com.watcha.itunes.mapper

import com.watcha.domain.model.Track
import com.watcha.itunes.model.TrackUiModel

fun Track.toUi(state: Boolean) = TrackUiModel(
    trackNumber = this.trackNumber,
    trackName = this.trackName,
    collectionName = this.collectionName,
    artistName = this.artistName,
    artwork = this.artwork,
    isFavorite = state
)

fun TrackUiModel.toDomain() = Track(
    trackNumber = this.trackNumber ?: 0,
    trackName = this.trackName ?: "",
    collectionName = this.collectionName ?: "",
    artistName = this.artistName ?: "",
    artwork = this.artwork ?: ""
)