package com.daisy.foodorder.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daisy.foodorder.data.ApiResponse
import com.daisy.foodorder.data.repositories.ProductRepository
import com.daisy.foodorder.domain.Defaults.PRODUCT_QUANTITY_RANGE
import com.daisy.foodorder.domain.Ingredient
import com.daisy.foodorder.domain.OrderItem
import com.daisy.foodorder.domain.Product
import com.daisy.foodorder.domain.totalCost
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductConfigurationViewModel @Inject constructor(
    private val repository: ProductRepository,
) : ViewModel() {
    private val _isDataLoaded: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isDataLoaded: StateFlow<Boolean> get() = _isDataLoaded

    private val _productDetails: MutableStateFlow<Product?> = MutableStateFlow(null)
    val productDetails: StateFlow<Product?> = _productDetails

    private val _extraIngredients: MutableStateFlow<List<Ingredient>> = MutableStateFlow(listOf())
    val extraIngredients: MutableStateFlow<List<Ingredient>> = _extraIngredients

    private val _productQuantity: MutableStateFlow<Int> =
        MutableStateFlow(PRODUCT_QUANTITY_RANGE.first)
    val productQuantity: StateFlow<Int> get() = _productQuantity

    val totalCost = combine(
        productDetails,
        extraIngredients,
        productQuantity,
        ::Triple
    )
        .filter { (product, _, _) ->
            product != null
        }
        .map { (product, ingredients, quantity) ->
            calculateTotalCost(product!!, ingredients, quantity)
        }
        .flowOn(Dispatchers.Default)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = 0.0
        )

    val configuredProduct: OrderItem
        get() = productDetails.value?.let { product ->
            OrderItem(
                product = product.copy(
                    extraIngredients = getSelectedIngredients(extraIngredients.value)
                ),
                quantity = productQuantity.value,
                totalCost = totalCost.value.toFloat()
            )
        } ?: throw IllegalStateException("Product details must not be null")

    private fun getSelectedIngredients(items: List<Ingredient>): List<Ingredient> =
        items.filter { item -> item.quantity != 0 }


    fun fetchDetails(name: String, price: Float) = viewModelScope.launch {
        repository.getProduct(name, price)
            .filter { response -> response is ApiResponse.Success }
            .map { response -> response.data }
            .collect {
                _productDetails.value = it?.copy(name = name)
                it?.let {
                    _extraIngredients.value = it.extraIngredients
                }
                _isDataLoaded.value = true
            }
    }

    fun updateProductQuantity(adjustBy: Int) {
        _productQuantity.value += adjustBy
    }

    fun updateIngredientQuantity(id: Long, adjustBy: Int) {
        val index = _extraIngredients.value.indexOfFirst { it.id == id }
        if (index != -1) {
            val newList = _extraIngredients.value.toMutableList()
            newList[index] = newList[index].copy(quantity = newList[index].quantity + adjustBy)
            _extraIngredients.value = newList
        }
    }

    private fun calculateTotalCost(
        product: Product,
        ingredients: List<Ingredient>,
        quantity: Int
    ): Double {
        val price = product.originalPrice
        val ingredientsCost = ingredients.totalCost()

        val totalCost = (price + ingredientsCost) * quantity
        return String.format("%.2f", totalCost).toDouble()
    }
}