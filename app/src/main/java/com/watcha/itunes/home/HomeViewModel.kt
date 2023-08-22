package com.watcha.itunes.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.watcha.itunes.common.SingleLiveEvent
import com.watcha.domain.model.Track
import com.watcha.domain.usecase.track.CheckFavoriteTrackUseCase
import com.watcha.domain.usecase.track.GetTrackListUseCase
import com.watcha.domain.usecase.track.InsertTrackUseCase
import com.watcha.itunes.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.watcha.domain.Result
import com.watcha.domain.usecase.track.GetAllTrackUseCase
import com.watcha.itunes.mapper.toDomain
import com.watcha.itunes.model.TrackUiModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn

private const val TAG = "HomeViewModel_싸피"
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTrackListUseCase: GetTrackListUseCase,
    private val insertTrackUseCase: InsertTrackUseCase,
) : BaseViewModel(), HomeEventHandler {

    private val filter: MutableLiveData<Int> = MutableLiveData()

    private val _homeSideEffect = SingleLiveEvent<HomeSideEffects>()
    val homeSideEffects: LiveData<HomeSideEffects> get() = _homeSideEffect

    private val _track = MutableLiveData<TrackUiModel>()
    val track: LiveData<TrackUiModel> get() = _track

    private val _trackNumber = MutableLiveData(0)
    val trackNumber: LiveData<Int> get() = _trackNumber

    val trackList = filter.switchMap {
        getTrackListUseCase().cachedIn(viewModelScope)
    }

    fun getTrackList(v: Int) {
        filter.value = v
    }

    fun insertTrack() = baseViewModelScope.launch {
        track.value?.apply { insertTrackUseCase(this.toDomain()) }
    }


    override fun favoriteClickEvent(track: TrackUiModel) {
        _homeSideEffect.postValue(HomeSideEffects.ClickFavoriteTrack(track))
        _track.value = track
        _trackNumber.value = track.trackNumber
    }
}