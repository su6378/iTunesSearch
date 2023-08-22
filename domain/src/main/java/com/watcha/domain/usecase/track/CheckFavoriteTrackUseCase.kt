package com.watcha.domain.usecase.track

import com.watcha.domain.repository.TrackRepository
import javax.inject.Inject

class CheckFavoriteTrackUseCase @Inject constructor(
    private val trackRepository: TrackRepository
) {
    operator fun invoke(trackNumber: Int) = trackRepository.checkFavoriteTrack(trackNumber)
}