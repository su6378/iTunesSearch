package com.watcha.itunes.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.watcha.itunes.common.SingleLiveEvent
import com.watcha.domain.model.Track
import com.watcha.domain.usecase.track.GetTrackListUseCase
import com.watcha.domain.usecase.track.InsertTrackUseCase
import com.watcha.itunes.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTrackListUseCase: GetTrackListUseCase,
    private val insertTrackUseCase: InsertTrackUseCase
) : BaseViewModel(), HomeEventHandler {

    private val filter: MutableLiveData<Int> = MutableLiveData()

    private val _homeSideEffect = SingleLiveEvent<HomeSideEffects>()
    val homeSideEffects: LiveData<HomeSideEffects> get() = _homeSideEffect

    private val _track = MutableLiveData<Track>()
    val track: LiveData<Track> get() = _track

    val trackList = filter.switchMap {
        getTrackListUseCase().cachedIn(viewModelScope)
    }
    
    fun getTrackList(v: Int) {
        filter.value = v
    }

    fun insertTrack() = baseViewModelScope.launch{
        track.value?.apply { insertTrackUseCase(this) }
    }

    override fun favoriteClickEvent(track: Track) {
        _homeSideEffect.postValue(HomeSideEffects.ClickFavoriteTrack(track))
        _track.value = track
    }
}