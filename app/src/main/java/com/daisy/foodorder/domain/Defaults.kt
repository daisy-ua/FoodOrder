package com.daisy.foodorder.domain

object Defaults {
    val DEFAULT_PRODUCT = Product(
        id = 0,
        name = "",
        description = "",
        type = "",
        extraIngredients = emptyList(),
        price = 0f,
        currency = "",
    )
}