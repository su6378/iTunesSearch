package com.watcha.itunes.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
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

    private val filter: MutableLiveData<Int> = MutableLiveData()

    val trackList = filter.switchMap { it ->
        getTrackListUseCase().cachedIn(viewModelScope)
    }
    
    fun getTrackList(v: Int) {
        filter.value = v
    }

    override fun favoriteClickEvent(questionId: Int) {
    }

}