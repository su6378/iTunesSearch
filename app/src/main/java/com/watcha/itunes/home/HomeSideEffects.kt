package com.watcha.itunes.home

import com.watcha.domain.model.Track


sealed class HomeSideEffects{
    class ClickFavoriteTrack(val track: Track) : HomeSideEffects()
}
