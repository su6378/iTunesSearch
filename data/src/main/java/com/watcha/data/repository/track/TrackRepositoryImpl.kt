package com.watcha.data.repository.track

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.watcha.data.mapper.toData
import com.watcha.data.mapper.toDomain
import com.watcha.data.repository.track.local.TrackLocalDataSource
import com.watcha.data.repository.track.remote.TrackRemoteDataSource
import com.watcha.domain.Result
import com.watcha.domain.model.Track
import com.watcha.domain.repository.TrackRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

private const val TAG = "TrackRepositoryImpl_싸피"
internal class TrackRepositoryImpl @Inject constructor(
    private val trackRemoteDataSource: TrackRemoteDataSource,
    private val trackLocalDataSource: TrackLocalDataSource
) : TrackRepository {
    override fun getTrackList(): LiveData<PagingData<Track>> = trackRemoteDataSource.getTrackList()

    override suspend fun insertTrack(track: Track) {
        trackLocalDataSource.insertTrack(track.toData())
    }

    override fun checkFavoriteTrack(trackNumber: Int): Flow<Result<Int>> = flow {
        emit(Result.Loading)
        trackLocalDataSource.checkFavoriteTrack(trackNumber).collect {
            emit(Result.Success(it))
        }
    }.catch { e ->
        emit(Result.Error(e))
    }

    override fun getAllTrack(): Flow<Result<List<Track>>> = flow {
        emit(Result.Loading)

        trackLocalDataSource.getAllTrack().collect{ list ->
            if (list.isEmpty()) emit(Result.Empty)
            else emit(Result.Success(list.map { it.toDomain() }))
        }
    }.catch { e ->
        emit(Result.Error(e))
    }

    override suspend fun deleteTrack(trackNumber: Int) = trackLocalDataSource.deleteTrack(trackNumber)
}