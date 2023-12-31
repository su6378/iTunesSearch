package com.watcha.domain.repository

import androidx.paging.PagingData
import com.watcha.domain.model.Track
import kotlinx.coroutines.flow.Flow
import com.watcha.domain.Result

interface TrackRepository {
    fun getTrackList(offset: Int): Flow<Result<List<Track>>>
    fun getAllFavoriteTrack(): Flow<Result<List<Track>>>
    suspend fun update(track: Track)
    fun getTracksByOffset(): Flow<PagingData<Track>>
}