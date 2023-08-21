package com.watcha.domain.repository

import androidx.paging.PagingData
import com.watcha.domain.model.DomainTrackResponse
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun getTrackList(): Flow<PagingData<DomainTrackResponse>>
}