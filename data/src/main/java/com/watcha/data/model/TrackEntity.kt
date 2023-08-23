package com.watcha.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "track_table")
data class TrackEntity(
    @PrimaryKey
    var offset: Int = 0,
    var trackNumber: Int = 0,
    var trackName: String? = "",
    var collectionName: String? = "",
    var artistName: String? = "",
    var artwork: String? = "",
    var isFavorite: Int = 0
)
