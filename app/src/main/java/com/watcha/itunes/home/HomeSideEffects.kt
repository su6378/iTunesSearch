package com.watcha.itunes.home


sealed class HomeSideEffects{
    class ClickFavoriteTrack(val questionId : Int) : HomeSideEffects()
}
