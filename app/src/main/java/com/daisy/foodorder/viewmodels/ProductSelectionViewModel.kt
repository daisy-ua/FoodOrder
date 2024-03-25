package com.daisy.foodorder.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daisy.foodorder.data.ApiResponse
import com.daisy.foodorder.data.repositories.ProductRepository
import com.daisy.foodorder.domain.Product
import com.daisy.foodorder.domain.ProductType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductSelectionViewModel @Inject constructor(
    private val productRepository: ProductRepository,
) : ViewModel() {
    private val productsCache = mutableMapOf<String, ApiResponse<List<Product>>>()

    private val _categoryResponse: MutableStateFlow<ApiResponse<List<ProductType>>> =
        MutableStateFlow(ApiResponse.Loading())

    val categories: StateFlow<List<ProductType>> = _categoryResponse
        .map { response -> (response as? ApiResponse.Success)?.data ?: emptyList() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private var _selectedCategoryIndex: MutableStateFlow<Int> = MutableStateFlow(0)
    val selectedCategoryIndex: StateFlow<Int> get() = _selectedCategoryIndex

    private var _selectedCategoryKey = combine(
        categories,
        selectedCategoryIndex,
        ::Pair
    )
        .filter { it.first.isNotEmpty() }
        .distinctUntilChanged()
        .map { (categories, id) -> categories[id].key }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000), "")

    fun updateSelectedCategory(index: Int) {
        _selectedCategoryIndex.value = index
    }

    private val _productsResponse: MutableStateFlow<ApiResponse<List<Product>>> =
        MutableStateFlow(ApiResponse.Loading())
    val products: StateFlow<List<Product>> = _productsResponse
        .map { response -> (response as? ApiResponse.Success)?.data ?: emptyList() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    init {
        fetchCategories()

        fetchProducts()
    }

    private fun fetchProducts() = viewModelScope.launch {
        _selectedCategoryKey
            .filter { it.isNotEmpty() }
            .distinctUntilChanged()
            .flatMapLatest { categoryKey ->
                val cachedResponse = productsCache[categoryKey]
                if (cachedResponse != null) {
                    flowOf(cachedResponse)
                } else {
                    productRepository.getProducts(categoryKey)
                        .onEach { response ->
                            if (response is ApiResponse.Success) {
                                productsCache[categoryKey] =
                                    response
                            }
                        }
                }
                    .catch { e -> emit(ApiResponse.Error(e)) }
            }
            .collect {
                _productsResponse.value = it
            }
    }

    private fun fetchCategories() = viewModelScope.launch {
//        productRepository.getCategories()
//            .catch {
//                _categoryResponse.value =
//                    ApiResponse.Error(Throwable(it.message ?: "Something went wrong"))
//                Log.d("daisy-ua", "error: ${it.message}")
//            }
//            .collect {
//                _categoryResponse.value = it
//            }
        _categoryResponse.value = ApiResponse.Success(
            listOf(
                ProductType("pizza"),
                ProductType("burger"),
                ProductType("salad"),
                ProductType("sandwich")
            )
        )
    }

}