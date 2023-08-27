package com.rique.walkseller.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rique.walkseller.domain.Seller
import com.rique.walkseller.ui.compose.ProductsList
import com.rique.walkseller.ui.compose.ProductsScreenCover
import com.rique.walkseller.ui.compose.SellerSection
import com.rique.walkseller.ui.viewModel.ProductsViewModel

@Composable
fun ProductsScreen(
    viewModel: ProductsViewModel,
    seller: Seller
) {
    val productsState = viewModel.productsState.value

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        ProductsScreenCover(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
        SellerSection(
            seller = seller,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        Divider(modifier = Modifier.padding(vertical = 8.dp))
        ProductsList(products = productsState.products, modifier = Modifier.fillMaxSize())

        // Cart section
        // Replace with your Cart modal
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//                .align(Alignment.BottomCenter)
//        ) {
//            Button(onClick = { /* Open Cart modal */ }) {
//                Text(text = "View Cart")
//            }
//        }
        LaunchedEffect(seller.id) {
            viewModel.loadProducts(seller.id)
        }
    }
}
