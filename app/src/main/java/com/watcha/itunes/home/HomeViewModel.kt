package com.watcha.itunes.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.watcha.itunes.common.SingleLiveEvent
import com.watcha.domain.model.Track
import com.watcha.domain.usecase.track.GetAllTrackUseCase
import com.watcha.domain.usecase.track.GetTrackListUseCase
import com.watcha.domain.usecase.track.InsertTrackUseCase
import com.watcha.itunes.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.watcha.domain.Result
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn

private const val TAG = "HomeViewModel_싸피"

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTrackListUseCase: GetTrackListUseCase, // remote
    private val getAllTrackUseCase: GetAllTrackUseCase, // local
    private val insertTrackUseCase: InsertTrackUseCase,
) : BaseViewModel(), HomeEventHandler {

    private val _homeSideEffect = SingleLiveEvent<HomeSideEffects>()
    val homeSideEffects: LiveData<HomeSideEffects> get() = _homeSideEffect

    private val _track = MutableLiveData<Track>()
    val track: LiveData<Track> get() = _track

    private val _trackNumber = MutableLiveData(0)
    val trackNumber: LiveData<Int> get() = _trackNumber

    private val _getTrackListResult = MutableLiveData<Result<List<Track>>>(Result.Uninitialized)
    val getTrackListResult: LiveData<Result<List<Track>>> get() = _getTrackListResult

    private val _offset = MutableLiveData(0)
    val offset: LiveData<Int> get() = _offset

    var trackList =
        getAllTrackUseCase()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = Result.Uninitialized
            ).asLiveData()

    fun getTrackList() = baseViewModelScope.launch {
        getTrackListUseCase(_offset.value!!).collectLatest {
            _getTrackListResult.value = it
        }
    }

    fun nextOffset() {
        _offset.value = _offset.value!! + 30
    }

    fun insertTrack(tracks : List<Track>) = baseViewModelScope.launch {
        insertTrackUseCase(tracks)
    }


    override fun favoriteClickEvent(track: Track) {
        _homeSideEffect.postValue(HomeSideEffects.ClickFavoriteTrack(track))
        _track.value = track
        _track.value!!.isFavorite = 1
        _trackNumber.value = track.trackNumber
    }
}