package com.watcha.data.repository.track

import android.util.Log
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

internal class TrackRepositoryImpl @Inject constructor(
    private val trackRemoteDataSource: TrackRemoteDataSource,
    private val trackLocalDataSource: TrackLocalDataSource
) : TrackRepository {

    override fun getTrackList(offset: Int): Flow<Result<List<Track>>> = flow {
        emit(Result.Loading)

        trackRemoteDataSource.getTrackList(offset).collect { response ->
            if (response.resultCount > 0) {
                emit(Result.Success(response.tracks!!.mapIndexed { index, trackResponse ->
                    trackResponse.toDomain(
                        offset + index
                    )
                }))
            } else emit(Result.Empty)
        }
    }.catch { e ->
        emit(Result.Error(e))
    }

    override suspend fun insertTrack(tracks: List<Track>) {
        val list = tracks.map { it.toData() }.toTypedArray()
        trackLocalDataSource.insertTrack(*list)
    }

    override fun checkFavoriteTrack(trackNumber: Int): Flow<Result<Int>> = flow {
        emit(Result.Loading)
        trackLocalDataSource.checkFavoriteTrack(trackNumber).collect {
            emit(Result.Success(it))
        }
    }.catch { e ->
        emit(Result.Error(e))
    }

    override fun getAllTrack(offset: Int): Flow<Result<List<Track>>> = flow {
        emit(Result.Loading)

        trackLocalDataSource.getAllTrack(offset, offset + 29).collect { list ->
            if (list.isEmpty()) emit(Result.Empty)
            else emit(Result.Success(list.map { it.toDomain() }))
        }
    }.catch { e ->
        emit(Result.Error(e))
    }

    override fun getAllFavoriteTrack(): Flow<Result<List<Track>>> = flow {
        emit(Result.Loading)

        trackLocalDataSource.getAllFavoriteTrack().collect() { list ->
            if (list.isEmpty()) emit(Result.Empty)
            else emit(Result.Success(list.map { it.toDomain() }))
        }
    }.catch { e ->
        emit(Result.Error(e))
    }

    override suspend fun update(track: Track) = trackLocalDataSource.updateTrack(track.toData())

    override suspend fun deleteTrack(trackNumber: Int) =
        trackLocalDataSource.deleteTrack(trackNumber)
}