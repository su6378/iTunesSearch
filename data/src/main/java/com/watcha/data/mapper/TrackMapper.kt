package com.watcha.data.mapper

import com.watcha.data.model.TrackEntity
import com.watcha.data.model.TrackResponse
import com.watcha.domain.model.Track

internal fun TrackResponse.toDomain(offset: Int) = Track(
    offset = offset,
    trackNumber = this.trackNumber ?: 0,
    trackName = this.trackName ?: "",
    collectionName = this.collectionName ?: "",
    artistName = this.artistName ?: "",
    artwork = this.artworkUrl100 ?: ""
)

internal fun TrackEntity.toDomain() = Track(
    offset = this.offset,
    trackNumber = this.trackNumber,
    trackName = this.trackName ?: "",
    collectionName = this.collectionName ?: "",
    artistName = this.artistName ?: "",
    artwork = this.artwork ?: "",
    isFavorite = this.isFavorite
)

internal fun Track.toData() = TrackEntity(
    offset = this.offset,
    trackNumber = this.trackNumber,
    trackName = this.trackName ?: "",
    collectionName = this.collectionName ?: "",
    artistName = this.artistName ?: "",
    artwork = this.artwork ?: "",
    isFavorite = this.isFavorite
)