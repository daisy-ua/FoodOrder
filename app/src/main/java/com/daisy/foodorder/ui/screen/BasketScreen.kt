package com.daisy.foodorder.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.daisy.foodorder.domain.OrderItem
import com.daisy.foodorder.ui.component.BackIconButton
import com.daisy.foodorder.ui.component.MainTopAppBar
import com.daisy.foodorder.ui.component.product.OrderProductPreview
import com.daisy.foodorder.ui.theme.FoodOrderTheme
import com.daisy.foodorder.viewmodels.BasketViewModel

@Composable
fun BasketScreen(
    onUpClicked: () -> Unit,
    viewModel: BasketViewModel,
) {
    val order by viewModel.order.collectAsState()
    val totalCost by viewModel.totalCost.collectAsState()

    Scaffold(
        topBar = {
            MainTopAppBar(
                title = "FoodOrder",
                navigationAction = {
                    BackIconButton(onUpClicked)
                })
        }
    ) {
        if (order.isEmpty()) {
            EmptyBasket()
        } else {
            BasketContent(
                modifier = Modifier.padding(it),
                order = order,
                totalCost = totalCost,
                onRemoveClicked = viewModel::removeFromOrder,
                onCheckoutClicked = viewModel::checkoutOrder
            )
        }
    }
}

@Composable
fun BasketContent(
    modifier: Modifier,
    order: List<OrderItem>,
    totalCost: Float,
    onRemoveClicked: (Int) -> Unit,
    onCheckoutClicked: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .then(modifier),
        ) {
            itemsIndexed(
                items = order,
                key = { _, item -> item.hashCode() }
            ) { index, item ->
                OrderProductPreview(
                    orderItem = item,
                    onRemoveClicked = { onRemoveClicked(index) }
                )
            }
        }

        CheckoutCard(
            totalCost = totalCost,
            onCheckoutClicked = onCheckoutClicked
        )
    }
}

@Composable
fun CheckoutCard(
    totalCost: Float,
    onCheckoutClicked: () -> Unit,
) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(0.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 18.dp, vertical = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Text(
                    text = "Total:",
                    modifier = Modifier.padding(end = 2.dp),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                Text(
                    text = "$$totalCost", style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }

            Button(onClick = onCheckoutClicked) {
                Text(text = "CHECKOUT")
            }
        }
    }
}

@Composable
fun EmptyBasket() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Your cart is empty",
            style = MaterialTheme.typography.headlineSmall,
        )
    }
}

@Composable
@Preview(showBackground = true)
fun BasketScreenPreview() {
    FoodOrderTheme(darkTheme = false) {
//        BasketScreen()
    }
}