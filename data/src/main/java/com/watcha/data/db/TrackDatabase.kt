package com.watcha.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.watcha.data.model.TrackEntity

@Database(entities = [TrackEntity::class], version = 1)
abstract class TrackDatabase: RoomDatabase() {
    abstract fun TrackDao(): TrackDao
}