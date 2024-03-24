package com.daisy.foodorder.data.network.models

import kotlinx.serialization.Serializable

@Serializable
data class ProductTypeDto(
    val id: Long,
    val name: String,
)