package com.daisy.foodorder.data.network.services

import com.daisy.foodorder.data.ApiResponse
import com.daisy.foodorder.data.network.models.ProductTypeDto
import kotlinx.coroutines.flow.Flow

interface ProductCategoryService {
    fun getCategories(): Flow<ApiResponse<List<ProductTypeDto>>>
}