package com.watcha.itunes.home

import com.watcha.domain.model.Track

interface HomeEventHandler {
    fun favoriteClickEvent(track: Track)
}