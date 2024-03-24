package com.daisy.foodorder.data.network.services

import com.daisy.foodorder.data.ApiResponse
import com.daisy.foodorder.data.network.models.ProductDto
import kotlinx.coroutines.flow.Flow

interface ProductService {
    fun getProducts(
        category: String,
        limit: Int = 10,
        offset: Int = 0
    ): Flow<ApiResponse<List<ProductDto>>>

    fun getProduct(id: Long): ProductDto
}