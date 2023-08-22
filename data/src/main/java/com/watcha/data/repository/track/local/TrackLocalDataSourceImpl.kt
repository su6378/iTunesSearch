package com.watcha.data.repository.track.local

import com.watcha.data.db.TrackDao
import com.watcha.data.model.TrackEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class TrackLocalDataSourceImpl @Inject constructor(
    private val trackDao: TrackDao
) : TrackLocalDataSource {
    override suspend fun insertTrack(track: TrackEntity) = trackDao.insertTrack(track)
    override fun checkFavoriteTrack(trackNumber: Int): Flow<Int> =
        trackDao.checkFavoriteTrack(trackNumber)

    override fun getAllTrack(): Flow<List<TrackEntity>> = trackDao.getAllTrack()
}