package com.watcha.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.watcha.data.api.SearchApi
import com.watcha.data.paging.GetTrackListPagingSource
import com.watcha.domain.model.DomainTrackResponse
import com.watcha.domain.repository.SearchRepository
import javax.inject.Inject

internal class SearchRepositoryImpl @Inject constructor(
    private val searchApi: SearchApi
) : SearchRepository {
    override fun getTrackList() = Pager(
        config = PagingConfig(
            pageSize = 1,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { GetTrackListPagingSource(searchApi) }
    ).liveData
}