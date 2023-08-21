package com.watcha.data.mapper

import com.watcha.data.model.TrackResponse
import com.watcha.domain.model.DomainTrackResponse

internal fun TrackResponse.toDomain() = DomainTrackResponse(
    trackNumber = this.trackNumber,
    trackName = this.trackName,
    collectionName = this.collectionName,
    artistName = this.artistName,
    artwork = this.artworkUrl60
)
