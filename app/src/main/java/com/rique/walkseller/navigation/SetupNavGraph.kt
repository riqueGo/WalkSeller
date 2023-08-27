package com.rique.walkseller.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rique.walkseller.domain.Seller
import com.rique.walkseller.ui.screen.MapScreen
import com.rique.walkseller.ui.screen.ProductsScreen
import com.rique.walkseller.ui.viewModel.MapViewModel

val LocalNavController = staticCompositionLocalOf<NavController?> { null }

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun SetupNavGraph(mapViewModel: MapViewModel) {
    val navController = rememberNavController()

    CompositionLocalProvider(LocalNavController provides navController) {
        NavHost(
            navController = navController,
            startDestination = NavDestination.MapScreen.route
        ) {
            composable(NavDestination.MapScreen.route) {
                MapScreen(viewModel = mapViewModel)
            }
            composable(
                route = NavDestination.ProductsScreen.route,
                arguments = listOf(navArgument("sellerId") { type = NavType.StringType })
            ) {
                val seller = navController.previousBackStackEntry?.savedStateHandle?.get<Seller>("seller")
                if (seller != null) {
                    ProductsScreen(
                        viewModel = hiltViewModel(),
                        seller = seller
                    )
                }
            }
        }
    }
}

