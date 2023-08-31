package com.rique.walkseller.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rique.walkseller.ui.viewModel.OrderViewModel

@Composable
fun OrderBottomSheetContent(
    orderViewModel: OrderViewModel,
) {
    val orderState = orderViewModel.orderState.value
    val orderBottomSheetState = orderViewModel.orderBottomSheetState.value
    val formattedTotalPrice = String.format("%.2f", orderState.totalOrderPrice)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                if (orderBottomSheetState.isOpenOrderDetails) {
                    orderState.productById.values.forEach { productDto ->
                        Text(
                            text = "${productDto.quantity}x ${productDto.name} - $${productDto.totalPrice}",
                            fontSize = 16.sp,
                        )
                    }
                }
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
            Column {
                Button(
                    onClick = {
                        orderViewModel.setIsOpenOrderDetails(!orderBottomSheetState.isOpenOrderDetails)
                        orderViewModel.adjustSheetBottomOrderDetailHeight()
                    },
                ) {
                    Text(text = if (orderBottomSheetState.isOpenOrderDetails) "hide" else "Details")
                }
                if (orderBottomSheetState.isOpenOrderDetails) {
                    Button(
                        onClick = {},
                    ) {
                        Text(text = "Call")
                    }
                }
            }
        }
    }
}