package com.daisy.foodorder.domain

data class Ingredient(
    val id: Long,

    val name: String,

    val price: Float,

    val currency: String,
)