package com.daisy.foodorder.data

sealed class ApiResponse<T>(
    val data: T? = null,
    val throwable: Throwable? = null
) {
    class Success<T>(data: T) : ApiResponse<T>(data)
    class Loading<T> : ApiResponse<T>()
    class Error<T>(throwable: Throwable, data: T? = null) : ApiResponse<T>(data, throwable)
}