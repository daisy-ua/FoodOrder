package com.daisy.foodorder.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IngredientDto(
    val id: Long,

    val name: String,

    val price: Float,

    @SerialName("currencyCode")
    val currency: String,
)