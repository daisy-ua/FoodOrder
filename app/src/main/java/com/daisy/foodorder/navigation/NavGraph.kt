package com.daisy.foodorder.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.daisy.foodorder.navigation.AppDestination.PRODUCT_NAME_KEY
import com.daisy.foodorder.navigation.AppDestination.PRODUCT_PRICE_PARAM
import com.daisy.foodorder.navigation.AppDestination.PRODUCT_SELECTION_ROUTE
import com.daisy.foodorder.ui.screen.BasketScreen
import com.daisy.foodorder.ui.screen.ProductConfigurationScreen
import com.daisy.foodorder.ui.screen.ProductSelectionScreen

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = PRODUCT_SELECTION_ROUTE.name,
) {
    val actions = remember(navController) { NavigationActions(navController) }

    val viewModelStoreOwner: ViewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(PRODUCT_SELECTION_ROUTE.name) { navBackStackEntry ->
            ProductSelectionScreen(
                onItemClick = { name, price ->
                    actions.navigateToProductConfigurationScreen(name, price, navBackStackEntry)
                },
                onBasketClicked = { actions.navigateToBasketScreen(navBackStackEntry) }
            )
        }

        composable(
            route = "${PRODUCT_SELECTION_ROUTE.name}/{${PRODUCT_NAME_KEY.name}}?${PRODUCT_PRICE_PARAM.name}={${PRODUCT_PRICE_PARAM.name}}",
            arguments = listOf(
                navArgument(PRODUCT_NAME_KEY.name) {
                    type = NavType.StringType
                },
                navArgument(PRODUCT_PRICE_PARAM.name) {
                    type = NavType.FloatType
                    defaultValue = 0
                },
            )
        ) { navBackStackEntry ->
            val arguments = requireNotNull(navBackStackEntry.arguments)
            val name = arguments.getString(PRODUCT_NAME_KEY.name).orEmpty()
            val price = arguments.getFloat(PRODUCT_PRICE_PARAM.name)

            ProductConfigurationScreen(
                name = name,
                price = price,
                onBasketClicked = { actions.navigateToBasketScreen(navBackStackEntry) },
                onUpClick = { actions.navigateUp(navBackStackEntry) },
                orderViewModel = hiltViewModel(viewModelStoreOwner)
            )
        }

        composable(AppDestination.BASKET_ROUTE.name) { navBackStackEntry ->
            BasketScreen(
                onUpClicked = { actions.navigateUp(navBackStackEntry) },
                viewModel = hiltViewModel(viewModelStoreOwner)
            )
        }
    }
}