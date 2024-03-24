package com.daisy.foodorder.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
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
import com.daisy.foodorder.BuildConfig
import com.daisy.foodorder.ui.component.BasketIconButton
import com.daisy.foodorder.ui.component.MainTopAppBar
import com.daisy.foodorder.ui.component.product.ProductPreview
import com.daisy.foodorder.ui.theme.FoodOrderTheme
import com.daisy.foodorder.viewmodels.ProductSelectionViewModel

@Composable
fun ProductSelectionScreen(
    onItemClick: (Long) -> Unit,
    viewModel: ProductSelectionViewModel = hiltViewModel<ProductSelectionViewModel>()
) {
//    val products by viewModel.products.collectAsState()

    Scaffold(
        topBar = {
            MainTopAppBar(
                title = "FoodOrder",
                actions = {
                    BasketIconButton { 
                        viewModel.getCategories()
                    }
                })
        }
    ) {
        Column {
            ChipChain(Modifier.padding(paddingValues = it))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 16.dp)
            ) {
                items(7) {
                    ProductPreview { }
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChipChain(modifier: Modifier) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        items(3) {
            FilterChip(
                selected = false,
                onClick = { /*TODO*/ },
                label = { Text(text = "Chip $it") },
                modifier = Modifier.padding(end = 12.dp)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ProductSelectionScreenPreview() {
    FoodOrderTheme(darkTheme = false) {
//        ProductSelectionScreen({})
    }
}