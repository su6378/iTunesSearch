package com.watcha.domain.usecase.track

import com.watcha.domain.model.Track
import com.watcha.domain.repository.TrackRepository
import javax.inject.Inject

class InsertTrackUseCase @Inject constructor(
    private val trackRepository: TrackRepository
) {
    suspend operator fun invoke(track: Track) = trackRepository.insertTrack(track)
}