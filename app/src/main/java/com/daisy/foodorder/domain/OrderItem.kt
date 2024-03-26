package com.daisy.foodorder.domain

data class OrderItem(
    val product: Product,
    val quantity: Int,
    val totalCost: Float,
)