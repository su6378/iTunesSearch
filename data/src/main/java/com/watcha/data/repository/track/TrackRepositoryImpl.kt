package com.watcha.data.repository.track

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.watcha.data.mapper.toData
import com.watcha.data.repository.track.local.TrackLocalDataSource
import com.watcha.data.repository.track.remote.TrackRemoteDataSource
import com.watcha.domain.model.Track
import com.watcha.domain.repository.TrackRepository
import javax.inject.Inject

internal class TrackRepositoryImpl @Inject constructor(
    private val trackRemoteDataSource: TrackRemoteDataSource,
    private val trackLocalDataSource: TrackLocalDataSource
) : TrackRepository {
    override fun getTrackList(): LiveData<PagingData<Track>> = trackRemoteDataSource.getTrackList()

    override suspend fun insertTrack(track: Track) {
        trackLocalDataSource.insertTrack(track.toData())
    }
}