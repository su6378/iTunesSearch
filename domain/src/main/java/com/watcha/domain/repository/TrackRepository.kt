package com.watcha.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.watcha.domain.model.Track

interface TrackRepository {
    fun getTrackList(): LiveData<PagingData<Track>>
    suspend fun insertTrack(track: Track)
}