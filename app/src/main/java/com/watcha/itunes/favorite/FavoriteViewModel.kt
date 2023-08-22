package com.watcha.itunes.favorite

import androidx.lifecycle.viewModelScope
import com.watcha.domain.Result
import com.watcha.domain.model.Track
import com.watcha.domain.usecase.track.GetAllTrackUseCase
import com.watcha.itunes.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getAllTrackUseCase: GetAllTrackUseCase
): BaseViewModel() {

    var favoriteList: StateFlow<Result<List<Track>>> =
        getAllTrackUseCase()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = Result.Uninitialized
            )
}