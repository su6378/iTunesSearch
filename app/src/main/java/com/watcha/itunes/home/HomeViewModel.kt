package com.watcha.itunes.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.watcha.domain.usecase.search.GetTrackListUseCase
import com.watcha.itunes.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTrackListUseCase: GetTrackListUseCase
) : BaseViewModel(), HomeEventHandler {

    private val filter: MutableLiveData<Int> = MutableLiveData()

    val trackList = filter.switchMap {
        getTrackListUseCase().cachedIn(viewModelScope)
    }
    
    fun getTrackList(v: Int) {
        filter.value = v
    }

    override fun favoriteClickEvent(questionId: Int) {
    }

}