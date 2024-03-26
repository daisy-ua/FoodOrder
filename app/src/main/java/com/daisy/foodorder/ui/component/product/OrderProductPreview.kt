package com.daisy.foodorder.ui.component.product

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.daisy.foodorder.domain.Ingredient
import com.daisy.foodorder.domain.OrderItem
import com.daisy.foodorder.ui.theme.FoodOrderTheme

@Composable
fun OrderProductPreview(
    orderItem: OrderItem,
    onRemoveClicked: () -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(0.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(start = 16.dp, top = 16.dp, bottom = 16.dp),
        ) {
            Box(
                modifier = Modifier
                    .height(150.dp)
                    .width(130.dp)
                    .background(MaterialTheme.colorScheme.tertiary)
            )

            Column(
                modifier = Modifier.padding(start = 16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                ProductInfo(orderItem, modifier = Modifier.padding(end = 16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 4.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = onRemoveClicked,
                    ) {
                        Text(text = "Remove", color = Color.Red.copy(.7f))
                    }
                }
            }
        }
    }
}

@Composable
fun ProductInfo(
    orderItem: OrderItem,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top,
        ) {
            Text(
                text = orderItem.product.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )

            Text(
                text = "$${orderItem.totalCost}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Quantity",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground.copy(.6f),
            )

            Text(
                text = orderItem.quantity.toString(),
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium,
            )
        }

        if (orderItem.product.extraIngredients.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Extra",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground.copy(.6f),
                    modifier = Modifier.weight(.3f)
                )

                Text(
                    text = ingredientsDisplayString(orderItem.product.extraIngredients),
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.weight(.65f),
                    textAlign = TextAlign.End
                )
            }
        }
    }
}

private fun ingredientsDisplayString(items: List<Ingredient>): String =
    items.joinToString(separator = ",\n") { item ->
        "${item.name} x${item.quantity}"
    }

@Composable
@Preview(showBackground = true)
fun OrderProductPreviewPreview() {
    FoodOrderTheme(darkTheme = false) {
//        OrderProductPreview()
    }
}