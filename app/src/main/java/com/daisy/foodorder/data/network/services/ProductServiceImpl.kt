package com.daisy.foodorder.data.network.services

import com.daisy.foodorder.data.ApiResponse
import com.daisy.foodorder.data.network.ApiPath
import com.daisy.foodorder.data.network.ApiPath.PRODUCT_TYPE
import com.daisy.foodorder.data.network.models.ProductDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductServiceImpl @Inject constructor(
    private val httpClient: HttpClient
) : ProductService {
    override fun getProducts(
        category: String,
        limit: Int,
        offset: Int
    ): Flow<ApiResponse<List<ProductDto>>> = flow {
        emit(ApiResponse.Loading())
        try {
            emit(
                ApiResponse.Success(
                    httpClient.get(ApiPath.PRODUCTS) {
                        parameter("offset", offset)
                        parameter("limit", limit)
                        parameter(PRODUCT_TYPE, category)
                    }.body()
                )
            )
        } catch (e: Exception) {
            emit(ApiResponse.Error(e))
        }
    }

    override fun getProduct(id: Long): ProductDto {
        TODO("Not yet implemented")
    }

}