package com.watcha.data.repository.track.local

import com.watcha.data.db.TrackDao
import com.watcha.data.model.TrackEntity
import javax.inject.Inject

internal class TrackLocalDataSourceImpl @Inject constructor(
    private val trackDao: TrackDao
) : TrackLocalDataSource {
    override suspend fun insertTrack(track: TrackEntity) {
        return trackDao.insertTrack(track)
    }
}