package com.watcha.domain.usecase.track

import com.watcha.domain.repository.TrackRepository
import javax.inject.Inject

class GetAllFavoriteTrackUseCase @Inject constructor(
    private val trackRepository: TrackRepository
) {
    operator fun invoke() = trackRepository.getAllFavoriteTrack()
}