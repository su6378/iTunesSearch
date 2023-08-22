package com.watcha.itunes.home

import com.watcha.itunes.model.TrackUiModel


sealed class HomeSideEffects{
    class ClickFavoriteTrack(val track: TrackUiModel) : HomeSideEffects()
}
