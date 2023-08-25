package com.rique.walkseller.routes

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rique.walkseller.ui.screen.MapScreen
import com.rique.walkseller.ui.screen.ProductsScreen
import com.rique.walkseller.ui.viewModel.MapViewModel

object NavDestination {
    const val MapScreen = "map"
    const val ProductsScreen = "products"
}

@Composable
fun SetupNavGraph(mapViewModel: MapViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavDestination.MapScreen
    ) {
        composable(NavDestination.MapScreen) {
            MapScreen(mapViewModel, navController)
        }
        composable(
            route = "${NavDestination.ProductsScreen}/{sellerId}",
            arguments = listOf(navArgument("sellerId") { type = NavType.StringType })
        ) { backStackEntry ->
            val sellerId = backStackEntry.arguments?.getString("sellerId")
            ProductsScreen(
                viewModel = hiltViewModel(),
                sellerId = sellerId ?: "",
                navigateBack = { navController.popBackStack() }
            )
        }
    }
}

