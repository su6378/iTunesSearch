package com.watcha.data.repository.track.remote

import com.watcha.data.model.SearchResponseDto
import kotlinx.coroutines.flow.Flow

internal interface TrackRemoteDataSource{
    fun getTrackList(offset: Int): Flow<SearchResponseDto>
}