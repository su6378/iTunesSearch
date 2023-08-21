package com.watcha.domain.usecase.search

import com.watcha.domain.repository.SearchRepository
import javax.inject.Inject

class GetTrackListUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    operator fun invoke() = searchRepository.getTrackList()
}