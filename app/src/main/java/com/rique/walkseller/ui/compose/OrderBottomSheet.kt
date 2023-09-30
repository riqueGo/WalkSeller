package com.rique.walkseller.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rique.walkseller.R
import com.rique.walkseller.di.LocalOrderViewModelProvider

@Composable
fun ProductsReviewList() {
    val orderViewModel = LocalOrderViewModelProvider.current
    val products = orderViewModel.orderState.value.productById.values.toList()
    Column(
        Modifier
            .height(200.dp)
            .fillMaxWidth()
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            items(products) { productDto ->
                Text(
                    text = "${productDto.quantity}x ${productDto.name} - R$${productDto.totalPrice}",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
        Divider(modifier = Modifier.padding(16.dp))
        TotalPriceAndItems(
            orderViewModel.getFormattedTotalPrice(),
            orderViewModel.getTotalProductsQuantityAsString()
        )
    }
}

@Composable
fun TotalPriceAndItems(totalPrice: String, totalQuantity: String) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = "Preço Total: R$${totalPrice}",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Text(
            text = "Quantidade Total: $totalQuantity",
            fontWeight = FontWeight.Light,
            fontSize = 16.sp
        )
    }
}


@Composable
fun OrderPrice(modifier: Modifier = Modifier) {
    val orderViewModel = LocalOrderViewModelProvider.current
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        TotalPriceAndItems(
            orderViewModel.getFormattedTotalPrice(),
            orderViewModel.getTotalProductsQuantityAsString()
        )
        Button(
            onClick = {
                orderViewModel.setIsOpenOrderDetails(true)
            },
            modifier = Modifier.align(Alignment.Bottom)
        ) {
            Text(text = stringResource(R.string.see_details))
        }
    }
}

@Composable
fun OrderBottomSheetContent() {
    val orderViewModel = LocalOrderViewModelProvider.current
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        OrderPrice()

        if (orderViewModel.orderBottomSheetState.value.isOpenOrderDetails) {
            GenericAlertDialog(
                onDismissRequest = { orderViewModel.setIsOpenOrderDetails(false) },
                onConfirmation = { orderViewModel.sendOrder(context) },
                dialogTitle = { Text(text = stringResource(R.string.order_details)) },
                dialogText = { ProductsReviewList() },
                icon = {
                    Icon(
                        Icons.Default.Done,
                        contentDescription = stringResource(R.string.order_details)
                    )
                }
            )
        }
    }
}