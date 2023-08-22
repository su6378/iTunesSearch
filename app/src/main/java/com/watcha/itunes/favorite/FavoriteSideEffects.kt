package com.watcha.itunes.favorite

import com.watcha.domain.model.Track

sealed class FavoriteSideEffects{
    class ClickDeleteFavoriteTrack(val track: Track) : FavoriteSideEffects()
}
