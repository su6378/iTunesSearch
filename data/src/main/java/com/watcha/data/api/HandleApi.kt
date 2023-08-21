package com.watcha.data.api

import com.watcha.domain.NetworkResult

internal inline fun <T> handleApi(transform: () -> T) =
    try {
        NetworkResult.Success(transform.invoke())
    } catch (e: Exception) {
        NetworkResult.Error(e)
    }