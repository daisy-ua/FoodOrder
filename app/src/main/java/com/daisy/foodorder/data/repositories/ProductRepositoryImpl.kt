package com.daisy.foodorder.data.repositories

import com.daisy.foodorder.data.ApiResponse
import com.daisy.foodorder.data.network.models.ProductTypeDto
import com.daisy.foodorder.data.network.services.ProductCategoryService
import com.daisy.foodorder.data.network.services.ProductService
import com.daisy.foodorder.domain.Product
import com.daisy.foodorder.domain.ProductType
import com.daisy.foodorder.mappers.toDomain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.reflect.KFunction1

class ProductRepositoryImpl @Inject constructor(
    private val productService: ProductService,
    private val categoryService: ProductCategoryService,
    private val dispatcher: CoroutineDispatcher,
) : ProductRepository {
    override fun getProducts(category: String): Flow<ApiResponse<List<Product>>> {
        return productService.getProducts(category)
            .map { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        val data = response.data?.map { it.toDomain() } ?: emptyList()
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
            .flowOn(dispatcher)
    }

    override fun getCategories(): Flow<ApiResponse<List<ProductType>>> {
        return categoryService.getCategories()
            .mapResponse(dispatcher, ProductTypeDto::toDomain)
    }

}

private fun <In, Out> Flow<ApiResponse<List<In>>>.mapResponse(
    dispatcher: CoroutineDispatcher,
    mapper: KFunction1<In, Out>,
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