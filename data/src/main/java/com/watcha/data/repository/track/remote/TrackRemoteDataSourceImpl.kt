package com.watcha.data.repository.track.remote

import com.watcha.data.api.SearchApi
import com.watcha.data.model.SearchResponseDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class TrackRemoteDataSourceImpl @Inject constructor(
    private val searchApi: SearchApi
) : TrackRemoteDataSource {
    override fun getTrackList(offset: Int): Flow<SearchResponseDto> = flow {
        emit(searchApi.getTrackList("NewJeans", "song", 30, offset))
    }
}