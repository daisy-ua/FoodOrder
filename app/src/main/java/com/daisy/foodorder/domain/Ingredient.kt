package com.daisy.foodorder.domain

data class Ingredient(
    val id: Long,

    val name: String,

    val price: Float,

    val quantity: Int,

    val currency: String,
)