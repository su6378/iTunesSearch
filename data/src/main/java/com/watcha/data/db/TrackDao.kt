package com.watcha.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.watcha.data.model.TrackEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackDao {
    // Favorite 트랙 추가
    @Insert(onConflict = OnConflictStrategy.REPLACE) // 이미 저장된 항목이 있을 경우 덮어쓰기
    suspend fun insertTrack(track: TrackEntity)

    // Favorite Track 조회
    @Query("SELECT trackNumber FROM track_table WHERE trackNumber = :trackNumber")
    fun checkFavoriteTrack(trackNumber: Int): Flow<Int>

    @Query("SELECT * FROM track_table")
    fun getAllTrack(): Flow<List<TrackEntity>>
}