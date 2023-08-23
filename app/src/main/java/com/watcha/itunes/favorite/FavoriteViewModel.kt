package com.watcha.itunes.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.watcha.domain.Result
import com.watcha.domain.model.Track
import com.watcha.domain.usecase.track.DeleteTrackUseCase
import com.watcha.domain.usecase.track.GetAllFavoriteTrackUseCase
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
    private val deleteTrackUseCase: DeleteTrackUseCase
) : BaseViewModel(), FavoriteEventHandler {

    private val _favoriteSideEffect = SingleLiveEvent<FavoriteSideEffects>()
    val favoriteSideEffect: LiveData<FavoriteSideEffects> get() = _favoriteSideEffect

    private val _deleteTrack = MutableLiveData<Track>()
    val deleteTrack: LiveData<Track> get() = _deleteTrack

    var favoriteList =
        getAllFavoriteTrackUseCase()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = Result.Uninitialized
            ).asLiveData()

    fun deleteTrack(trackNumber: Int) = baseViewModelScope.launch {
        deleteTrackUseCase(trackNumber)
    }

    override fun deleteTrackClickEvent(track: Track) {
        _deleteTrack.value = track
        _favoriteSideEffect.postValue(FavoriteSideEffects.ClickDeleteFavoriteTrack(track))
    }
}