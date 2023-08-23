package com.watcha.itunes.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.watcha.itunes.common.SingleLiveEvent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

abstract class BaseViewModel : ViewModel() {

    private val _errorEvent = SingleLiveEvent<Throwable>()
    val errorEvent: LiveData<Throwable> get() = _errorEvent

    private val errorHandler = CoroutineExceptionHandler { CoroutineContext, throwable ->
        viewModelScope.launch(CoroutineContext) {
            _errorEvent.postValue(throwable)
        }
    }
    protected val baseViewModelScope: CoroutineScope = viewModelScope + errorHandler
}
