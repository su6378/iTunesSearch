package com.watcha.itunes.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.watcha.domain.Result
import com.watcha.domain.model.Track
import com.watcha.domain.usecase.track.GetAllFavoriteTrackUseCase
import com.watcha.domain.usecase.track.UpdateTrackUseCase
import com.watcha.itunes.base.BaseViewModel
import com.watcha.itunes.common.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getAllFavoriteTrackUseCase: GetAllFavoriteTrackUseCase,
    private val updateTrackUseCase: UpdateTrackUseCase,
) : BaseViewModel(), FavoriteEventHandler {

    private val _favoriteSideEffect = SingleLiveEvent<FavoriteSideEffects>()
    val favoriteSideEffect: LiveData<FavoriteSideEffects> get() = _favoriteSideEffect

    private val _track = MutableLiveData<Track>()
    val track: LiveData<Track> get() = _track

    var favoriteList =
        getAllFavoriteTrackUseCase()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = Result.Uninitialized
            ).asLiveData()

    fun updateTrack() = baseViewModelScope.launch {
        updateTrackUseCase(_track.value!!)
    }

    override fun deleteTrackClickEvent(track: Track) {
        _track.value = track
        _favoriteSideEffect.postValue(FavoriteSideEffects.ClickDeleteFavoriteTrack(track))
        _track.value!!.isFavorite = if (track.isFavorite == 1) 0 else 1
    }
}