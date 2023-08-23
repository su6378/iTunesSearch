package com.watcha.data.repository.track.local

import com.watcha.data.model.TrackEntity
import kotlinx.coroutines.flow.Flow

internal interface TrackLocalDataSource {
   suspend fun insertTrack(vararg tracks: TrackEntity)
   fun checkFavoriteTrack(trackNumber: Int): Flow<Int>
   fun getAllTrack(start: Int, end: Int): Flow<List<TrackEntity>>
   fun getAllFavoriteTrack(): Flow<List<TrackEntity>>
   suspend fun updateTrack(track: TrackEntity)
}