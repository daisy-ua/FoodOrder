package com.daisy.foodorder.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    @SerialName("product_id")
    val id: Long,

    @SerialName("product_name")
    val name: String,

    val description: String?,

    @SerialName("category")
    val type: String,

    @SerialName("extra_ingredients")
    val extraIngredients: List<IngredientDto>,

    val price: Float,

    @SerialName("currencyCode")
    val currency: String,
)

