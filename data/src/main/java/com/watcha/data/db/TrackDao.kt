package com.watcha.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.watcha.data.model.TrackEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackDao {
    // 트랙 추가 (데이터 일괄 삽입을 위해 vararg 키워드 사용)
    @Insert(onConflict = OnConflictStrategy.IGNORE) // 이미 저장된 데이터가 있는 경우 무시
    suspend fun insertTrack(vararg tracks: TrackEntity)

    // Favorite 트랙 추가
    @Insert(onConflict = OnConflictStrategy.REPLACE) // 이미 저장된 항목이 있을 경우 덮어쓰기
    suspend fun insertFavoriteTrack(track: TrackEntity)

    // Favorite Track 조회
    @Query("SELECT trackNumber FROM track_table WHERE trackNumber = :trackNumber")
    fun checkFavoriteTrack(trackNumber: Int): Flow<Int>

    @Query("SELECT * FROM track_table")
    fun getAllTrack(): Flow<List<TrackEntity>>

    @Query("SELECT * FROM track_table WHERE isFavorite = 1")
    fun getAllFavoriteTrack(): Flow<List<TrackEntity>>

    // Favorite Track 삭제
    @Query("DELETE FROM track_table WHERE trackNumber = :trackNumber")
    suspend fun deleteTrack(trackNumber: Int)
}