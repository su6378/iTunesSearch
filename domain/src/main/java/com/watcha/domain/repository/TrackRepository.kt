package com.watcha.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.watcha.domain.model.Track
import kotlinx.coroutines.flow.Flow
import com.watcha.domain.Result

interface TrackRepository {
    fun getTrackList(): LiveData<PagingData<Track>>
    suspend fun insertTrack(track: Track)
    fun checkFavoriteTrack(trackNumber: Int): Flow<Result<Int>>
    fun getAllTrack(): Flow<Result<List<Track>>>
    suspend fun deleteTrack(trackNumber: Int)
}