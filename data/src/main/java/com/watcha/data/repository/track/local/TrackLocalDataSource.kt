package com.watcha.data.repository.track.local

import com.watcha.data.model.TrackEntity

internal interface TrackLocalDataSource {
   suspend fun insertTrack(track: TrackEntity)
}