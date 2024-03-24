package com.daisy.foodorder.domain

data class Product(
    val id: Long,

    val name: String,

    val description: String,

    val type: String,

    val extraIngredients: List<Ingredient>,

    val price: Float,

    val currency: String,
)