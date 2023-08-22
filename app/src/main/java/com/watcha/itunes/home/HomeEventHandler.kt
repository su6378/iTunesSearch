package com.watcha.itunes.home

import com.watcha.itunes.model.TrackUiModel

interface HomeEventHandler {
    fun favoriteClickEvent(track: TrackUiModel)
}