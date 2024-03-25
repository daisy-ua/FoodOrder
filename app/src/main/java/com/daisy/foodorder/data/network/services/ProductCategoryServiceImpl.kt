package com.daisy.foodorder.data.network.services

import com.daisy.foodorder.data.ApiResponse
import com.daisy.foodorder.data.network.ApiPath
import com.daisy.foodorder.data.network.models.ProductTypeDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductCategoryServiceImpl @Inject constructor(
    private val httpClient: HttpClient
) : ProductCategoryService {
    override fun getCategories(): Flow<ApiResponse<List<ProductTypeDto>>> = fetchRequest {
        httpClient.get(ApiPath.CATEGORIES).body()
    }
}
