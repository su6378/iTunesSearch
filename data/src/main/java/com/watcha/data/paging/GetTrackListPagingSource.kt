package com.watcha.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.watcha.data.api.SearchApi
import com.watcha.data.db.TrackDao
import com.watcha.data.mapper.toData
import com.watcha.data.mapper.toDomain
import com.watcha.domain.model.Track
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

internal class GetTrackListPagingSource @Inject constructor(
    private val trackDao: TrackDao,
    private val searchApi: SearchApi
) : PagingSource<Int, Track>() {
    override fun getRefreshKey(state: PagingState<Int, Track>): Int? {
        return state.anchorPosition?.let { achorPosition ->
            state.closestPageToPosition(achorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(achorPosition)?.nextKey?.minus(1)
        }
    }

    // 데이터 로드
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Track> {
        // LoadParams : 로드할 키와 항목 수 , LoadResult : 로드 작업의 결과
        return try {
            // 로딩 애니메이션 효과를 위해 지연
            if (params.key == null) delay(3000)
            else delay(1500)

            // 키 값이 없을 경우 기본값을 사용함
            val currentPage = params.key ?: 0

            // 데이터를 제공하는 인스턴스의 메소드 사용
            var response = trackDao.getTracksByOffset(currentPage, currentPage + 29) ?: emptyList()
            val responseData = mutableListOf<Track>()

            if (response.isEmpty()) { // Local DB에 없는 경우 -> search API 요청
                val remoteResponse = searchApi.getTrackList("NewJeans", "song", 30, currentPage)
                if (remoteResponse.resultCount > 0) { // iTunes search API 데이터가 있는 경우
                    // Local DB에 데이터 삽입
                    val list = remoteResponse.tracks!!.mapIndexed { index, trackResponse ->
                        trackResponse.toDomain(index + currentPage).toData()
                    }.toTypedArray()
                    trackDao.insertTrack(*list)

                    responseData.addAll(
                        searchApi.getTrackList(
                            "NewJeans",
                            "song",
                            30,
                            currentPage
                        ).tracks!!.mapIndexed { index, trackResponse -> trackResponse.toDomain(index + currentPage) }
                    )
                }
            } else responseData.addAll(response.map { it.toDomain() }) // Local DB에 있는 데이터 삽입

            // 페이지 넘버값 증가
            val nextKey =
                // 마지막 페이지, 데이터 여부 확인
                if (responseData.isEmpty()) {
                    null
                } else {
                    currentPage + 30
                }

            LoadResult.Page(
                data = responseData,
                prevKey = null,
                nextKey = nextKey
            )

            // 로드에 실패 시 LoadResult.Error 반환
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}