package com.rique.walkseller.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rique.walkseller.domain.Order

@Composable
fun OrderBottomSheetContent(
    state: Order
) {
    val formattedTotalPrice = String.format("%.2f", state.totalOrderPrice)
    Box(
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
                    text = "Total Items: ${state.totalProductsQuantity}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
            Button(
                onClick = {},
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Text(text = "Details")
            }
        }
    }
}