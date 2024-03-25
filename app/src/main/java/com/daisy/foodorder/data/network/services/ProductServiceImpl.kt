package com.daisy.foodorder.data.network.services

import com.daisy.foodorder.data.ApiResponse
import com.daisy.foodorder.data.network.ApiPath
import com.daisy.foodorder.data.network.ApiPath.PRODUCT_NAME
import com.daisy.foodorder.data.network.ApiPath.PRODUCT_PRICE
import com.daisy.foodorder.data.network.ApiPath.PRODUCT_TYPE
import com.daisy.foodorder.data.network.models.ProductDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductServiceImpl @Inject constructor(
    private val httpClient: HttpClient
) : ProductService {
    override fun getProducts(
        category: String,
        limit: Int,
        offset: Int
    ): Flow<ApiResponse<List<ProductDto>>> = fetchRequest {
        httpClient.get(ApiPath.PRODUCTS) {
            parameter("offset", offset)
            parameter("limit", limit)
            parameter(PRODUCT_TYPE, category)
        }.body()
    }

    override fun getProduct(name: String, price: Float): Flow<ApiResponse<ProductDto>> =
        fetchRequest {
            httpClient.get(ApiPath.PRODUCT_DETAILS) {
                parameter(PRODUCT_NAME, name)
                parameter(PRODUCT_PRICE, price)
            }.body()
        }
}