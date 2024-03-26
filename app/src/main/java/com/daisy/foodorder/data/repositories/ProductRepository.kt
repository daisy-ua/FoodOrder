package com.daisy.foodorder.data.repositories

import com.daisy.foodorder.data.ApiResponse
import com.daisy.foodorder.domain.Order
import com.daisy.foodorder.domain.Product
import com.daisy.foodorder.domain.ProductType
import kotlinx.coroutines.flow.Flow


interface ProductRepository {
    fun getProducts(category: String): Flow<ApiResponse<List<Product>>>

    fun getProduct(name: String, price: Float): Flow<ApiResponse<Product?>>

    fun getCategories(): Flow<ApiResponse<List<ProductType>>>

    fun serializeOrder(order: Order): String
}