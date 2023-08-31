package com.rique.walkseller.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rique.walkseller.DI.LocalOrderViewModelProvider
import com.rique.walkseller.DI.LocalSheetStateProvider
import com.rique.walkseller.domain.Seller
import com.rique.walkseller.ui.compose.OrderBottomSheetContent
import com.rique.walkseller.ui.compose.ProductsList
import com.rique.walkseller.ui.compose.ProductsScreenCover
import com.rique.walkseller.ui.compose.SellerSection
import com.rique.walkseller.ui.viewModel.OrderViewModel
import com.rique.walkseller.ui.viewModel.ProductsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsScreen(
    viewModel: ProductsViewModel,
    orderViewModel: OrderViewModel,
    seller: Seller,
) {
    val productsState = viewModel.productsState.value

    val orderState = orderViewModel.orderState.value
    val sheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.Hidden,
        skipHiddenState = false
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )

    BottomSheetScaffold(
        modifier = Modifier.fillMaxSize(),
        sheetDragHandle = null,
        sheetSwipeEnabled = false,
        sheetTonalElevation = 96.dp,
        sheetPeekHeight = orderViewModel.orderBottomSheetState.value.sheetHeight,
        sheetContent = { OrderBottomSheetContent(orderViewModel, sheetState) },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .padding()
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
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
            CompositionLocalProvider(
                LocalOrderViewModelProvider provides orderViewModel,
                LocalSheetStateProvider provides sheetState
            ) {
                ProductsList(
                    products = productsState.products,
                    modifier = Modifier.fillMaxSize()
                )
            }
            if (orderState.totalProductsQuantity > 0){
                Box(modifier = Modifier.height(96.dp))
            }
        }
        LaunchedEffect(seller.id) {
            viewModel.loadProducts(seller.id)
        }
    }
}
