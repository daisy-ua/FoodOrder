package com.daisy.foodorder.navigation

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.daisy.foodorder.navigation.AppDestination.PRODUCT_PRICE_PARAM
import com.daisy.foodorder.navigation.AppDestination.PRODUCT_SELECTION_ROUTE

class NavigationActions(navController: NavHostController) {
    val navigateToProductConfigurationScreen =
        { name: String, price: Float, from: NavBackStackEntry ->
            if (from.lifecycleIsResumed()) {
                navController.navigate(
                    "${PRODUCT_SELECTION_ROUTE.name}/$name?${PRODUCT_PRICE_PARAM.name}=$price"
                )
            }
        }

    val navigateUp: (from: NavBackStackEntry) -> Unit = { from ->
        if (from.lifecycleIsResumed()) {
            navController.navigateUp()
        }
    }
}

private fun NavBackStackEntry.lifecycleIsResumed() =
    this.getLifecycle().currentState == Lifecycle.State.RESUMED