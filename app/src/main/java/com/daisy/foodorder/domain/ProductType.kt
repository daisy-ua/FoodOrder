package com.daisy.foodorder.domain

data class ProductType(
    val key: String,

    val displayString: String,
) {

    constructor(key: String) : this(
        key = key,
        displayString = key.replaceFirstChar(Char::titlecase)
    )
}