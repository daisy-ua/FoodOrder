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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.daisy.foodorder.ui.component.BackIconButton
import com.daisy.foodorder.ui.component.BasketIconButton
import com.daisy.foodorder.ui.component.MainTopAppBar
import com.daisy.foodorder.ui.component.QuantitySelector
import com.daisy.foodorder.ui.theme.FoodOrderTheme

@Composable
fun ProductConfiguration() {
    Scaffold(
        topBar = {
            MainTopAppBar(
                title = "FoodOrder",
                actions = {
                    BasketIconButton { }
                },
                navigationAction = {
                    BackIconButton { }
                })
        }
    ) {
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
                    ProductInfo()
                }

                items(3) {
                    ExtraIngredientItem()
                }
            }

            CheckoutSummaryBottomBar({})
        }
    }
}

@Composable
fun ProductInfo() {
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
                text = "Burger",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .weight(.8f)
            )

            Text(
                text = "$12.00",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Text(
            text = "Dill pickle, cheddar cheese, tomato, red onion,",
            style = MaterialTheme.typography.bodyMedium,
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
fun ExtraIngredientItem() {
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
            Text(text = "Parmesan", maxLines = 1)
            Text(
                text = "+$2.00",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }

        QuantitySelector()
    }
}

@Composable
fun CheckoutSummaryBottomBar(onClick: () -> Unit) {
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
            QuantitySelector()

            Button(onClick = { onClick() }) {
                Text("ADD TO CART ($24.00)")
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ProductConfigurationPreview() {
    FoodOrderTheme(darkTheme = false) {
        ProductConfiguration()
    }
}