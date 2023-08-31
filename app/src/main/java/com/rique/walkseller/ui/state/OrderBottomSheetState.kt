package com.rique.walkseller.ui.state

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class OrderBottomSheetState(
    val isOpenOrderDetails: Boolean = false,
    val sheetHeight: Dp = 96.dp
)