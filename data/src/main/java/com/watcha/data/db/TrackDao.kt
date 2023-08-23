package com.watcha.data.db

import androidx.room.*
import com.watcha.data.model.TrackEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackDao {
    // 트랙 추가 (데이터 일괄 삽입을 위해 vararg 키워드 사용)
    @Insert(onConflict = OnConflictStrategy.IGNORE) // 이미 저장된 데이터가 있는 경우 무시
    suspend fun insertTrack(vararg tracks: TrackEntity)

    // Favorite 트랙 조회
    @Query("SELECT * FROM track_table WHERE isFavorite = 1")
    fun getAllFavoriteTrack(): Flow<List<TrackEntity>>

    // 르랙 수정 (Favorite 등록 or 삭제)
    @Update()
    suspend fun updateTrack(track: TrackEntity)

    // 해당 offset을 기준으로 데이터 조회
    @Query("SELECT * FROM track_table WHERE offset BETWEEN :start AND :end")
    fun getTracksByOffset(start: Int, end: Int): List<TrackEntity>?
}