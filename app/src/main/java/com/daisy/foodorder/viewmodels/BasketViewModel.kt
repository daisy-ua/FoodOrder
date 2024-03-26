package com.daisy.foodorder.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daisy.foodorder.domain.OrderItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class BasketViewModel @Inject constructor() : ViewModel() {
    private val _order: MutableStateFlow<List<OrderItem>> = MutableStateFlow(emptyList())
    val order: StateFlow<List<OrderItem>> get() = _order

    val totalCost: StateFlow<Float> = order
        .map(::calculateTotalCost)
        .flowOn(Dispatchers.Default)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = 0f
        )

    fun addToOrder(value: OrderItem) {
        val currentProducts = order.value.toMutableList()
        currentProducts.add(value)
        _order.value = currentProducts
    }

    fun removeFromOrder(index: Int) {
        val currentProducts = order.value.toMutableList()
        currentProducts.removeAt(index)
        _order.value = currentProducts
    }

    private fun calculateTotalCost(order: List<OrderItem>): Float =
        order.sumOf { it.totalCost.toDouble() }.let { totalCost ->
            String.format("%.2f", totalCost).toFloat()
        }
}