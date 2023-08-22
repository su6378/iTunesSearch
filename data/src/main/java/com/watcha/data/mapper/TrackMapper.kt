package com.watcha.data.mapper

import com.watcha.data.model.TrackEntity
import com.watcha.data.model.TrackResponse
import com.watcha.domain.model.Track

internal fun TrackResponse.toDomain() = Track(
    trackNumber = this.trackNumber ?: 0,
    trackName = this.trackName ?: "",
    collectionName = this.collectionName ?: "",
    artistName = this.artistName ?: "",
    artwork = this.artworkUrl100 ?: ""
)

internal fun TrackEntity.toDomain() = Track(
    trackNumber = this.trackNumber ?: 0,
    trackName = this.trackName ?: "",
    collectionName = this.collectionName ?: "",
    artistName = this.artistName ?: "",
    artwork = this.artwork ?: ""
)

internal fun Track.toData() = TrackEntity(
    trackNumber = this.trackNumber,
    trackName = this.trackName,
    collectionName = this.collectionName,
    artistName = this.artistName,
    artwork = this.artwork
)