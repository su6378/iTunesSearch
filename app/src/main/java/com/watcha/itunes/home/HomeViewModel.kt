package com.watcha.itunes.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.watcha.itunes.common.SingleLiveEvent
import com.watcha.domain.model.Track
import com.watcha.itunes.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.watcha.domain.Result
import com.watcha.domain.usecase.track.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

private const val TAG = "HomeViewModel_싸피"

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTrackListUseCase: GetTrackListUseCase, // remote
    private val getAllTrackUseCase: GetAllTrackUseCase, // local
    private val insertTrackUseCase: InsertTrackUseCase,
    private val updateTrackUseCase: UpdateTrackUseCase,
    private val getTrackByOffsetUseCase: GetTrackByOffsetUseCase
) : BaseViewModel(), HomeEventHandler {

    // 이벤트 처리 LiveData (SingleLiveEvent 사용한 이유는 한번만 호출하기 위해서)
    private val _homeSideEffect = SingleLiveEvent<HomeSideEffects>()
    val homeSideEffects: LiveData<HomeSideEffects> get() = _homeSideEffect

    // iTunes API -> 받아온 트랙리스트 response
    private val _getTrackListResult = SingleLiveEvent<Result<List<Track>>>()
    val getTrackListResult: LiveData<Result<List<Track>>> get() = _getTrackListResult

    // 다음으로 요청할 offset
    private val _offset = MutableLiveData(0)
    val offset: LiveData<Int> get() = _offset

    // 별 클릭 시 받아오는 track 정보
    private val _track = MutableLiveData<Track>()
    val track: LiveData<Track> get() = _track

    // local DB offset 기준 트랙 리스트
    private val _trackList = SingleLiveEvent<Result<List<Track>>>()
    val trackList: LiveData<Result<List<Track>>> get() = _trackList

    // 로딩 상태 체크 변수
    private val _loadingState = MutableLiveData(0)
    val loadingState: LiveData<Int> get() = _loadingState

    val temp = getTrackByOffsetUseCase().asLiveData()

    fun getTrackByOffset(): Flow<PagingData<Track>> {
       return getTrackByOffsetUseCase().cachedIn(viewModelScope)
    }

    // local(room) trackList 조회
    fun getLocalTrackList() = baseViewModelScope.launch {
        _trackList.postValue(Result.Loading)
        Log.d(TAG, "몇 번 일어나는지 체크: ")
        getAllTrackUseCase(_offset.value!!).collectLatest {
            delay(2000) // 2초간 지연 (로딩 상태 유지 로딩 애니메이션)
            _trackList.value = it
        }
    }

    // remote(iTunes API) trackList 조회
    fun getTrackList() = baseViewModelScope.launch {
        getTrackListUseCase(_offset.value!!).collectLatest {
            _getTrackListResult.value = it
        }
    }

    fun nextOffset(number: Int) {
        _offset.value = number
    }

    // local db trackList 삽입
    fun insertTrack(tracks: List<Track>) = baseViewModelScope.launch {
        insertTrackUseCase(tracks)
    }

    fun updateTrack() = baseViewModelScope.launch {
        updateTrackUseCase(_track.value!!)
    }

    // 로딩 상태 변경
    fun changeLoadingState(state: Int){
        _loadingState.value = state
    }

    override fun favoriteClickEvent(track: Track) {
        _homeSideEffect.postValue(HomeSideEffects.ClickFavoriteTrack(track))
        _track.value = track
        _track.value!!.isFavorite = if (track.isFavorite == 1) 0 else 1
    }
}