package com.daisy.foodorder.mappers

import com.daisy.foodorder.data.network.models.IngredientDto
import com.daisy.foodorder.data.network.models.OrderDto
import com.daisy.foodorder.data.network.models.OrderItemDto
import com.daisy.foodorder.data.network.models.ProductDto
import com.daisy.foodorder.domain.Ingredient
import com.daisy.foodorder.domain.Order
import com.daisy.foodorder.domain.OrderItem
import com.daisy.foodorder.domain.Product

fun OrderItem.toDto() = OrderItemDto(product.toDto(), quantity)

fun Order.toDto() = OrderDto(products.map { it.toDto() }, totalCost)

fun Product.toDto() = ProductDto(
    id,
    name,
    description,
    type,
    extraIngredients.map { it.toDto() },
    originalPrice,
    currency
)

fun Ingredient.toDto() = IngredientDto(id, name, price, currency)