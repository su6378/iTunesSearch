package com.watcha.domain.model

data class Track(
    val offset: Int = 0,
    val trackNumber: Int = 0,
    val trackName: String? = "",
    val collectionName: String? = "",
    val artistName: String? = "",
    val artwork: String? = "",
    var isFavorite: Int = 0
)
