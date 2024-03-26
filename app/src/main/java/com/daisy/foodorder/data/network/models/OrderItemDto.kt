package com.daisy.foodorder.data.network.models

import kotlinx.serialization.Serializable


@Serializable
data class OrderItemDto(
    val products: ProductDto,
    val quantity: Int,
)
