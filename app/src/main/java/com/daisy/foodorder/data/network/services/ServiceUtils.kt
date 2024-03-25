package com.daisy.foodorder.data.network.services

import com.daisy.foodorder.data.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal fun <T> fetchRequest(request: suspend () -> T): Flow<ApiResponse<T>> = flow {
    emit(ApiResponse.Loading())
    try {
        val result = request()
        emit(
            ApiResponse.Success(result)
        )
    } catch (e: Exception) {
        emit(ApiResponse.Error(e))
    }
}