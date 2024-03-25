package com.daisy.foodorder.ui.component.product

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.daisy.foodorder.domain.Product
import com.daisy.foodorder.ui.theme.FoodOrderTheme

@Composable
fun ProductPreview(
    item: Product,
    onClick: (Long) -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(0) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {
            Box(
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.tertiary)
                    .padding(bottom = 8.dp)
            )

            Text(
                text = item.name,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 2.dp)
            )

            Text(
                text = "$${item.price}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ProductPreviewPreview() {
    FoodOrderTheme(darkTheme = false) {
//        ProductPreview({})
    }
}