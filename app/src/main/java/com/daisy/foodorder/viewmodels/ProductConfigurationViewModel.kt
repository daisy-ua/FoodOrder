package com.daisy.foodorder.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daisy.foodorder.data.ApiResponse
import com.daisy.foodorder.data.repositories.ProductRepository
import com.daisy.foodorder.domain.Defaults.DEFAULT_PRODUCT
import com.daisy.foodorder.domain.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductConfigurationViewModel @Inject constructor(
    private val repository: ProductRepository,
) : ViewModel() {
    private val _productDetails: MutableStateFlow<Product?> = MutableStateFlow(null)
    val productDetails: StateFlow<Product?> = _productDetails

    fun fetchDetails(name: String, price: Float) = viewModelScope.launch {
        repository.getProduct(name, price)
            .filter { response -> response is ApiResponse.Success }
            .map { response -> response.data }
            .collect {
                _productDetails.value = it ?: DEFAULT_PRODUCT
            }
    }
}