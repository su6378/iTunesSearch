package com.watcha.data.mapper

import com.watcha.data.model.TrackInfo
import com.watcha.domain.model.DomainTrackResponse

internal fun TrackInfo.toDomain() = DomainTrackResponse(
    trackNumber = this.trackNumber,
    trackName = this.trackName,
    collectionName = this.collectionName,
    artistName = this.artistName,
    artwork = this.artworkUrl60
)
