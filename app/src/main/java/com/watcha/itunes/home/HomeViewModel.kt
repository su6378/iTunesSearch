package com.watcha.itunes.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.watcha.itunes.common.SingleLiveEvent
import com.watcha.domain.model.Track
import com.watcha.domain.usecase.search.GetTrackListUseCase
import com.watcha.itunes.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTrackListUseCase: GetTrackListUseCase
) : BaseViewModel(), HomeEventHandler {

    private val filter: MutableLiveData<Int> = MutableLiveData()

    // 초기값은 UiState.Loading
    private val _homeSideEffect = SingleLiveEvent<HomeSideEffects>()
    val homeSideEffects: LiveData<HomeSideEffects> get() = _homeSideEffect

    val trackList = filter.switchMap {
        getTrackListUseCase().cachedIn(viewModelScope)
    }
    
    fun getTrackList(v: Int) {
        filter.value = v
    }


    override fun favoriteClickEvent(track: Track) {
        _homeSideEffect.postValue(HomeSideEffects.ClickFavoriteTrack(track))
    }

}