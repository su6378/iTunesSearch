package com.watcha.itunes.favorite

import com.watcha.domain.model.Track

interface FavoriteEventHandler {
    fun deleteTrackClickEvent(track: Track)
}