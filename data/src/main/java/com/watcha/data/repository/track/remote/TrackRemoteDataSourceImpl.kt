package com.watcha.data.repository.track.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.watcha.data.api.SearchApi
import com.watcha.data.paging.GetTrackListPagingSource
import javax.inject.Inject

internal class TrackRemoteDataSourceImpl @Inject constructor(
    private val searchApi: SearchApi
): TrackRemoteDataSource {
    override fun getTrackList() =  Pager(
        config = PagingConfig(
            pageSize = 1,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { GetTrackListPagingSource(searchApi) }
    ).liveData
}