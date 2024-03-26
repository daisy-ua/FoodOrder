package com.daisy.foodorder.data.network.models

import kotlinx.serialization.Serializable

@Serializable
data class OrderDto(
    val products: List<OrderItemDto>,
    val totalCost: Float,
)
