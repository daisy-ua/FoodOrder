package com.daisy.foodorder.domain

data class Order(
    val products: List<OrderItem>,
    val totalCost: Float,
)
