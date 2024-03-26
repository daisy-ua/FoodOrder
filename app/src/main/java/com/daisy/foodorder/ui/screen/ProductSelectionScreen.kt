package com.daisy.foodorder.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.daisy.foodorder.domain.ProductType
import com.daisy.foodorder.ui.component.BasketIconButton
import com.daisy.foodorder.ui.component.MainTopAppBar
import com.daisy.foodorder.ui.component.product.ProductPreview
import com.daisy.foodorder.ui.theme.FoodOrderTheme
import com.daisy.foodorder.viewmodels.ProductSelectionViewModel

@Composable
fun ProductSelectionScreen(
    onItemClick: (String, Float) -> Unit,
    onBasketClicked: () -> Unit,
    viewModel: ProductSelectionViewModel = hiltViewModel<ProductSelectionViewModel>()
) {
    val categories by viewModel.categories.collectAsState()
    val selectedCategoryIndex by viewModel.selectedCategoryIndex.collectAsState()
    val products by viewModel.products.collectAsState()

    Scaffold(
        topBar = {
            MainTopAppBar(
                title = "FoodOrder",
                actions = {
                    BasketIconButton(onBasketClicked)
                })
        }
    ) {
        Column {
            ChipChain(
                modifier = Modifier.padding(paddingValues = it),
                data = categories,
                selectedTypeIndex = selectedCategoryIndex,
            ) { index -> viewModel.updateSelectedCategory(index) }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 16.dp)
            ) {
                items(
                    items = products,
                    key = { item -> item.id }
                ) { product ->
                    ProductPreview(product) {
                        onItemClick(product.name, product.originalPrice)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChipChain(
    modifier: Modifier,
    data: List<ProductType>,
    selectedTypeIndex: Int,
    onItemClick: (Int) -> Unit,
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        itemsIndexed(
            items = data,
            key = { _, item -> item.displayString }
        ) { index, category ->
            FilterChip(
                selected = index == selectedTypeIndex,
                onClick = { onItemClick(index) },
                label = { Text(text = category.displayString) },
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