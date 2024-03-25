package com.daisy.foodorder.data.repositories

import com.daisy.foodorder.data.ApiResponse
import com.daisy.foodorder.data.network.models.ProductDto
import com.daisy.foodorder.data.network.models.ProductTypeDto
import com.daisy.foodorder.data.network.services.ProductCategoryService
import com.daisy.foodorder.data.network.services.ProductService
import com.daisy.foodorder.domain.Product
import com.daisy.foodorder.domain.ProductType
import com.daisy.foodorder.mappers.toDomain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productService: ProductService,
    private val categoryService: ProductCategoryService,
    private val dispatcher: CoroutineDispatcher,
) : ProductRepository {
    override fun getProducts(category: String): Flow<ApiResponse<List<Product>>> {
        return productService.getProducts(category)
            .mapResponse(dispatcher, ProductDto::toDomain)
    }

    override fun getProduct(name: String, price: Float): Flow<ApiResponse<Product?>> {
        return productService.getProduct(name.split(" ").first(), price)
            .map { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        val data = response.data?.toDomain()
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
            }
    }

    override fun getCategories(): Flow<ApiResponse<List<ProductType>>> {
        return categoryService.getCategories()
            .mapResponse(dispatcher, ProductTypeDto::toDomain)
    }

}
