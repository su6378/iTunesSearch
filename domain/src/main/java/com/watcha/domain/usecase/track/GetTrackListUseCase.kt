package com.watcha.domain.usecase.track

import com.watcha.domain.repository.TrackRepository
import javax.inject.Inject

class GetTrackListUseCase @Inject constructor(
    private val trackRepository: TrackRepository
) {
    operator fun invoke(offset: Int) = trackRepository.getTrackList(offset)
}