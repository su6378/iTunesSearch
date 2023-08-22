package com.watcha.data.repository.track.remote

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.watcha.domain.model.Track

internal interface TrackRemoteDataSource{
    fun getTrackList(): LiveData<PagingData<Track>>
}