package com.watcha.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.watcha.data.model.TrackEntity

@Dao
interface TrackDao {
    // Favorite 트랙 추가
    @Insert(onConflict = OnConflictStrategy.REPLACE) // 이미 저장된 항목이 있을 경우 덮어쓰기
    suspend fun insertTrack(track: TrackEntity)
}