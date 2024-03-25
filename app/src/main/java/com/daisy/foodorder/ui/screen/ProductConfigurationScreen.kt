package com.daisy.foodorder.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.daisy.foodorder.domain.Defaults
import com.daisy.foodorder.domain.Ingredient
import com.daisy.foodorder.domain.Product
import com.daisy.foodorder.ui.component.BackIconButton
import com.daisy.foodorder.ui.component.BasketIconButton
import com.daisy.foodorder.ui.component.MainTopAppBar
import com.daisy.foodorder.ui.component.QuantitySelector
import com.daisy.foodorder.ui.theme.FoodOrderTheme
import com.daisy.foodorder.viewmodels.ProductConfigurationViewModel

@Composable
fun ProductConfigurationScreen(
    name: String,
    price: Float,
    onUpClick: () -> Unit,
    viewModel: ProductConfigurationViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = name) {
        viewModel.fetchDetails(name, price)
    }

    val productDetails by viewModel.productDetails.collectAsState()
    val shouldShowSuccess by viewModel.isDataLoaded.collectAsState()
    val ingredients by viewModel.extraIngredients.collectAsState()
    val productQuantity by viewModel.productQuantity.collectAsState()
    val totalCost by viewModel.totalCost.collectAsState()

    Scaffold(
        topBar = {
            MainTopAppBar(
                title = "FoodOrder",
                actions = {
                    BasketIconButton { }
                },
                navigationAction = {
                    BackIconButton { onUpClick() }
                })
        }
    ) {
        if (shouldShowSuccess) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(it)
                ) {
                    item {
                        ProductInfo(productDetails!!)
                    }

                    items(
                        items = ingredients,
                        key = { item -> item.id }
                    ) { ingredient ->
                        ExtraIngredientItem(
                            ingredient = ingredient,
                            onLessClicked = {
                                viewModel.addLessIngredient(ingredient.id)
                            },
                            onMoreClicked = {
                                viewModel.addMoreIngredient(ingredient.id)
                            })
                    }
                }

                CheckoutSummaryBottomBar(
                    totalCost = totalCost,
                    quantity = productQuantity,
                    onAddToCartClick = {},
                    onLessClicked = {
                        viewModel.addLessProductQuantity()
                    },
                    onMoreClicked = {
                        viewModel.addMoreProductQuantity()
                    }
                )
            }
        }
    }
}

@Composable
fun ProductInfo(
    product: Product,
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Box(
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.tertiary)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = product.name,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .weight(.8f)
            )

            Text(
                text = "$${product.price}",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Text(
            text = product.description,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 4,
            color = MaterialTheme.colorScheme.onBackground.copy(.6f),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "Extra Ingredients",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 24.dp, bottom = 0.dp)
        )
    }
}

@Composable
fun ExtraIngredientItem(
    ingredient: Ingredient,
    onLessClicked: () -> Unit,
    onMoreClicked: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(.6f)
        ) {
            Text(text = ingredient.name, maxLines = 1)
            Text(
                text = "+$${ingredient.price}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }

        QuantitySelector(
            value = ingredient.quantity,
            onLessClicked = onLessClicked,
            onMoreClicked = onMoreClicked
        )
    }
}

@Composable
fun CheckoutSummaryBottomBar(
    totalCost: Double,
    quantity: Int,
    onAddToCartClick: () -> Unit,
    onLessClicked: () -> Unit,
    onMoreClicked: () -> Unit,
) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(0.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            QuantitySelector(
                value = quantity,
                onLessClicked = onLessClicked,
                onMoreClicked = onMoreClicked,
                valueRange = Defaults.PRODUCT_QUANTITY_RANGE
            )

            Button(onClick = { onAddToCartClick() }) {
                Text("ADD TO CART ($$totalCost)")
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ProductConfigurationPreview() {
    FoodOrderTheme(darkTheme = false) {
//        ProductConfigurationScreen()
    }
}