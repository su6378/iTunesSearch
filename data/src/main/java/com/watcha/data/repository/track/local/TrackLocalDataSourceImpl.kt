package com.watcha.data.repository.track.local

import com.watcha.data.db.TrackDao
import com.watcha.data.model.TrackEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class TrackLocalDataSourceImpl @Inject constructor(
    private val trackDao: TrackDao
) : TrackLocalDataSource {
    override suspend fun insertTrack(vararg tracks: TrackEntity) = trackDao.insertTrack(*tracks)
    override fun getAllFavoriteTrack(): Flow<List<TrackEntity>> = trackDao.getAllFavoriteTrack()
    override suspend fun updateTrack(track: TrackEntity) = trackDao.updateTrack(track)
}