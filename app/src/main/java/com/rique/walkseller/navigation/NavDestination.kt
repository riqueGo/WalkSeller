package com.rique.walkseller.navigation

sealed class NavDestination(val route: String) {
    object MapScreen : NavDestination("map")
    object ProductsScreen : NavDestination("products/{sellerId}") {
        fun createRoute(sellerId: String): String = "products/$sellerId"
    }
}
