package com.daisy.foodorder.ui.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.daisy.foodorder.ui.theme.FoodOrderTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar(
    title: String,
    navigationAction: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
            )
        },
        navigationIcon = navigationAction,
        actions = actions,
    )
}

@Preview(showBackground = true)
@Composable
fun CreateAlarmScreenPreview() {
    FoodOrderTheme(darkTheme = false) {
        MainTopAppBar("FoodOrder")
    }
}