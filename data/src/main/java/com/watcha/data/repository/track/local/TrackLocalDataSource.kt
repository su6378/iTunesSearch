package com.watcha.data.repository.track.local

import com.watcha.data.model.TrackEntity
import kotlinx.coroutines.flow.Flow

internal interface TrackLocalDataSource {
   suspend fun insertTrack(vararg tracks: TrackEntity)
   suspend fun insertFavoriteTrack(track: TrackEntity)
   fun checkFavoriteTrack(trackNumber: Int): Flow<Int>
   fun getAllTrack(): Flow<List<TrackEntity>>
   fun getAllFavoriteTrack(): Flow<List<TrackEntity>>
   suspend fun deleteTrack(trackNumber: Int)
}