package com.watcha.itunes.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.watcha.domain.model.DomainTrackResponse
import com.watcha.domain.usecase.search.GetTrackListUseCase
import com.watcha.itunes.base.BaseViewModel
import com.watcha.itunes.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "HomeViewModel_싸피"
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTrackListUseCase: GetTrackListUseCase
) : BaseViewModel(), HomeEventHandler {

    private val _trackList = MutableLiveData<PagingData<DomainTrackResponse>>()
    val trackList: LiveData<PagingData<DomainTrackResponse>>
        get() = _trackList

    fun getTrackList(): Flow<PagingData<DomainTrackResponse>> {
        return getTrackListUseCase().cachedIn(viewModelScope)
    }

    override fun favoriteClickEvent(questionId: Int) {
    }

}