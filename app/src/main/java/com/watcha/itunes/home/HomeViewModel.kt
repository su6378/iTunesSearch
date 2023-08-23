package com.watcha.itunes.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.watcha.itunes.common.SingleLiveEvent
import com.watcha.domain.model.Track
import com.watcha.itunes.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.watcha.domain.usecase.track.*

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val updateTrackUseCase: UpdateTrackUseCase,
    private val getTrackByOffsetUseCase: GetTrackByOffsetUseCase
) : BaseViewModel(), HomeEventHandler {

    // 이벤트 처리 LiveData (SingleLiveEvent 사용한 이유는 한번만 호출하기 위해서)
    private val _homeSideEffect = SingleLiveEvent<HomeSideEffects>()
    val homeSideEffects: LiveData<HomeSideEffects> get() = _homeSideEffect

    // 별 클릭 시 받아오는 track 정보
    private val _track = MutableLiveData<Track>()
    val track: LiveData<Track> get() = _track

    // 페이징 기능으로 받아오는 track 리스트 정보
    val trackList = getTrackByOffsetUseCase().asLiveData()

    fun updateTrack() = baseViewModelScope.launch {
        updateTrackUseCase(_track.value!!)
    }

    override fun favoriteClickEvent(track: Track) {
        _homeSideEffect.postValue(HomeSideEffects.ClickFavoriteTrack(track))
        _track.value = track
        _track.value!!.isFavorite = if (track.isFavorite == 1) 0 else 1
    }
}