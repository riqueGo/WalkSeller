package com.rique.walkseller.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rique.walkseller.ui.viewModel.ProductsViewModel

@Composable
fun ProductsScreen(
    viewModel: ProductsViewModel,
    sellerId: String,
    navigateBack: () -> Unit = {}
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Products",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(vertical = 16.dp)
        )

//        LazyColumn {
//            items(state.products.size) { product ->
//                ProductItem(
//                    product = product,
//                    onClick = {
//                        viewModel.setSelectedProduct(product)
//                    }
//                )
//            }
//        }
//
//        productsState.selectedProduct?.let { selectedProduct ->
//            ProductDetailDialog(
//                product = selectedProduct,
//                onDismiss = {
//                    viewModel.setSelectedProduct(null)
//                }
//            )
//        }

        Button(
            onClick = navigateBack,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(text = "Go Back")
        }
    }

    LaunchedEffect(sellerId) {
        viewModel.loadProducts(sellerId)
    }
}
