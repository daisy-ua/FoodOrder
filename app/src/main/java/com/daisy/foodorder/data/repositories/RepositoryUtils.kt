package com.daisy.foodorder.data.repositories

import com.daisy.foodorder.data.ApiResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlin.reflect.KFunction1

internal fun <In, Out> Flow<ApiResponse<List<In>>>.mapResponse(
    dispatcher: CoroutineDispatcher,
    mapper: (In) -> Out,
) =
    map { response ->
        when (response) {
            is ApiResponse.Success -> {
                val data = response.data?.map { mapper(it) } ?: emptyList()
                ApiResponse.Success(data)
            }

            is ApiResponse.Error -> {
                val error = response.throwable ?: RuntimeException("Unknown error")
                ApiResponse.Error(error)
            }

            is ApiResponse.Loading -> {
                ApiResponse.Loading()
            }
        }
    }.flowOn(dispatcher)