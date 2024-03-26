package com.daisy.foodorder.mappers

import com.daisy.foodorder.data.network.models.IngredientDto
import com.daisy.foodorder.data.network.models.ProductDto
import com.daisy.foodorder.data.network.models.ProductTypeDto
import com.daisy.foodorder.domain.Ingredient
import com.daisy.foodorder.domain.Product
import com.daisy.foodorder.domain.ProductType

fun ProductDto.toDomain() =
    Product(
        id,
        name,
        description ?: "",
        type,
        extraIngredients.map { it.toDomain() },
        price,
        currency
    )

fun IngredientDto.toDomain() = Ingredient(id, name, price, 0, currency)

fun ProductTypeDto.toDomain() = ProductType(name)