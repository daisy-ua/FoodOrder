package com.daisy.foodorder.domain

data class Ingredient(
    val id: Long,

    val name: String,

    val price: Float,

    val quantity: Int,

    val currency: String,
)

fun List<Ingredient>.totalCost() = sumOf { ingredient ->
    (ingredient.price * ingredient.quantity).toDouble()
}.toFloat()