package com.daisy.foodorder.ui.theme.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.daisy.foodorder.ui.component.MainTopAppBar
import com.daisy.foodorder.ui.component.product.OrderProductPreview
import com.daisy.foodorder.ui.theme.FoodOrderTheme

@Composable
fun BasketScreen() {
    Scaffold(
        topBar = {
            MainTopAppBar(
                title = "FoodOrder",
                navigationAction = {
                    BackIconButton { }
                })
        }
    ) {
        if (false) {
            EmptyBasket()
        } else {
            BasketContent(modifier = Modifier.padding(it))
        }
    }
}

@Composable
fun BasketContent(modifier: Modifier) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .then(modifier),
        ) {
            items(3) {
                OrderProductPreview()
            }
        }

        CheckoutCard()
    }
}

@Composable
fun CheckoutCard() {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(0.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background),
            horizontalArrangement = Arrangement.End,
        ) {
            Row(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .padding(horizontal = 18.dp, vertical = 20.dp)
            ) {
                Text(
                    text = "Total:",
                    modifier = Modifier.padding(end = 2.dp),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                Text(
                    text = "$250.00", style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                )
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
        BasketScreen()
    }
}