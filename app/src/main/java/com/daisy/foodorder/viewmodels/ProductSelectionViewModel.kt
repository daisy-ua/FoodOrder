package com.daisy.foodorder.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daisy.foodorder.data.ApiResponse
import com.daisy.foodorder.data.repositories.ProductRepository
import com.daisy.foodorder.domain.Product
import com.daisy.foodorder.domain.ProductType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductSelectionViewModel @Inject constructor(
    private val productRepository: ProductRepository,
) : ViewModel() {
    private val _categories: MutableStateFlow<ApiResponse<List<ProductType>>> =
        MutableStateFlow(ApiResponse.Loading())
    val categories: StateFlow<ApiResponse<List<ProductType>>> get() = _categories

    private val _products: MutableStateFlow<ApiResponse<List<Product>>> =
        MutableStateFlow(ApiResponse.Loading())
    val products: StateFlow<ApiResponse<List<Product>>> get() = _products

    init {
//        getProducts()
    }

    fun getProducts() = viewModelScope.launch {
        productRepository.getProducts("salad")
            .catch {
                _products.value = ApiResponse.Error(Throwable(it.message ?: "Something went wrong"))
            }
            .collect {
                Log.d("daisy-ua", "getProducts: ${it.data}")
                _products.value = it
            }
    }

    fun getCategories() = viewModelScope.launch {
        productRepository.getCategories()
            .catch {
                _categories.value = ApiResponse.Error(Throwable(it.message ?: "Something went wrong"))
                Log.d("daisy-ua", "error: ${it.message}")
            }
            .collect {
                Log.d("daisy-ua", "getCategories: ${it.data}")
                _categories.value = it
            }
    }

}