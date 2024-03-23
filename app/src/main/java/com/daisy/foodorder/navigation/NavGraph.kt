package com.daisy.foodorder.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.daisy.foodorder.navigation.AppDestination.PRODUCT_SELECTION
import com.daisy.foodorder.navigation.AppDestination.PRODUCT_CONFIGURATION
import com.daisy.foodorder.ui.theme.screen.ProductSelectionScreen

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = PRODUCT_SELECTION.name,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(PRODUCT_SELECTION.name) { navBackStackEntry ->  
            ProductSelectionScreen({})
        }
    }
}