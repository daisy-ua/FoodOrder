package com.daisy.foodorder.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daisy.foodorder.data.ApiResponse
import com.daisy.foodorder.data.repositories.ProductRepository
import com.daisy.foodorder.domain.Defaults.PRODUCT_QUANTITY_RANGE
import com.daisy.foodorder.domain.Ingredient
import com.daisy.foodorder.domain.Product
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

//    private val _totalSum: MutableStateFlow<Float> = MutableStateFlow(0f)

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
            val price = product!!.price
            val ingredientsCost = ingredients.sumOf { ingredient ->
                (ingredient.price * ingredient.quantity).toDouble()
            }

            (price + ingredientsCost) * quantity
        }
        .flowOn(Dispatchers.Default)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = 0.0
        )


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

    fun addLessProductQuantity(adjustBy: Int = 1) {
        updateProductQuantity(-adjustBy)
    }

    fun addMoreProductQuantity(adjustBy: Int = 1) {
        updateProductQuantity(adjustBy)
    }

    fun addLessIngredient(id: Long, adjustBy: Int = 1) {
        updateIngredientQuantity(id, -adjustBy)
    }

    fun addMoreIngredient(id: Long, adjustBy: Int = 1) {
        updateIngredientQuantity(id, adjustBy)
    }

    private fun updateProductQuantity(adjustBy: Int) {
        _productQuantity.value += adjustBy
    }

    private fun updateIngredientQuantity(id: Long, adjustBy: Int) {
        val updatedIngredients = _extraIngredients.value.map { ingredient ->
            if (ingredient.id == id) {
                ingredient.copy(quantity = ingredient.quantity + adjustBy)
            } else {
                ingredient
            }
        }
        _extraIngredients.value = updatedIngredients
    }
}