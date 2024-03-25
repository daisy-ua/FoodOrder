package com.daisy.foodorder.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun QuantitySelector(
    value: Int,
    onLessClicked: () -> Unit,
    onMoreClicked: () -> Unit,
    valueRange: IntRange = 0..5
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AddLessButton(
            onClick = onLessClicked,
            enabled = value > valueRange.first
        )

        Text(
            text = value.toString(),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        AddMoreButton(
            onClick = onMoreClicked,
            enabled = value < valueRange.last
        )
    }
}