package com.daisy.foodorder.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun BasketIconButton(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Default.ShoppingCart,
            contentDescription = "Basket"
        )
    }
}

@Composable
fun BackIconButton(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Basket"
        )
    }
}

@Composable
fun AddLessButton(
    onClick: () -> Unit,
    enabled: Boolean = true,
    size: Dp = 34.dp,
    color: Color = MaterialTheme.colorScheme.primary
) {
    OutlinedButton(
        modifier = Modifier.size(size),
        enabled = enabled,
        shape = CircleShape,
        contentPadding = PaddingValues(0.dp),
        border = BorderStroke(1.dp, color),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = color),
        onClick = onClick,
    ) {
        Icon(
            imageVector = Icons.Default.KeyboardArrowDown,
            contentDescription = "Add less",
            modifier = Modifier.padding(2.dp)
        )
    }
}

@Composable
fun AddMoreButton(
    onClick: () -> Unit,
    enabled: Boolean = true,
    size: Dp = 34.dp,
    color: Color = MaterialTheme.colorScheme.primary
) {
    OutlinedButton(
        modifier = Modifier.size(size),
        enabled = enabled,
        shape = CircleShape,
        contentPadding = PaddingValues(0.dp),
        border = BorderStroke(1.dp, color),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = color),
        onClick = onClick,
    ) {
        Icon(
            imageVector = Icons.Default.KeyboardArrowUp,
            contentDescription = "Add more",
            modifier = Modifier.padding(2.dp)
        )
    }
}