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

    private val _loadingEvent = SingleLiveEvent<Boolean>()
    val loadingEvent: LiveData<Boolean> get() = _loadingEvent

    private val errorHandler = CoroutineExceptionHandler { CoroutineContext, throwable ->
        viewModelScope.launch(CoroutineContext) {
            _errorEvent.postValue(throwable)
        }
    }

    private val _errorFunction = SingleLiveEvent<Int>()
    val errorFunction: LiveData<Int> = _errorFunction


    /**
     * api 호출시 필요함, 실패하면 해당 로직으로 이동
     * 만약 로그인 오류라면 로그인 액티비티로 이동
     * */
    fun catchError(e: Throwable?) {
        viewModelScope.launch(errorHandler) {
            e?.let { exception ->
                when (exception) {
                    else -> _errorEvent.postValue(exception)
                }
            }
        }
    }

    fun setError(action: Int){
        viewModelScope.launch {
            _errorFunction.postValue(action)
        }
    }

    fun showLoading() {
        baseViewModelScope.launch {
            _loadingEvent.postValue(true)
        }
    }

    fun dismissLoading() {
        baseViewModelScope.launch {
            _loadingEvent.postValue(false)
        }
    }

    protected val baseViewModelScope: CoroutineScope = viewModelScope + errorHandler
}
