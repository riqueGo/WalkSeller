package com.rique.walkseller.DI

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavController
import com.rique.walkseller.ui.viewModel.OrderViewModel

val LocalOrderViewModelProvider = staticCompositionLocalOf<OrderViewModel> {
    error("No OrderViewModel provided")
}

val LocalNavControllerProvider = staticCompositionLocalOf<NavController> {
    error("No NavController provided")
}

@OptIn(ExperimentalMaterial3Api::class)
val LocalSheetStateProvider = staticCompositionLocalOf<SheetState> {
    error("No SheetStateProvider provided")
}