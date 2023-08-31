package com.rique.walkseller.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rique.walkseller.ui.viewModel.OrderViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderBottomSheetContent(
    orderViewModel: OrderViewModel,
    sheetState: SheetState
) {
    val orderState = orderViewModel.orderState.value
    val orderBottomSheetState = orderViewModel.orderBottomSheetState.value
    val formattedTotalPrice = String.format("%.2f", orderState.totalOrderPrice)
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "Total Price: $${formattedTotalPrice}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = "Total Items: ${orderState.totalProductsQuantity}",
                    fontWeight = FontWeight.Light,
                    fontSize = 16.sp
                )
            }
            Button(
                onClick = {
                    orderViewModel.setIsOpenOrderDetails(!orderBottomSheetState.isOpenOrderDetails)
                    orderViewModel.adjustSheetBottomOrderDetailHeight()
                },
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Text(text = if (orderBottomSheetState.isOpenOrderDetails) "Call" else "Details")
            }
        }
        if (orderBottomSheetState.isOpenOrderDetails) {
            Text(text = "Hello World")
            Text(text = "Hi")
        }
    }
}