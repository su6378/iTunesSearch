package com.watcha.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.watcha.domain.model.DomainTrackResponse

interface SearchRepository {
    fun getTrackList(): LiveData<PagingData<DomainTrackResponse>>
}