package com.rique.walkseller.ui.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState
import com.rique.walkseller.R
import com.rique.walkseller.utils.Utils
import com.rique.walkseller.ui.viewModel.SellerMarkersViewModel

@Composable
fun SellerMarkers(viewModel: SellerMarkersViewModel, navController: NavController) {
    val state = viewModel.sellerMarkersState.value
    val sellerIcon =
        Utils.bitmapDescriptorFromVector(LocalContext.current, R.drawable.delivery_dining)

    state.sellers.forEach { seller ->
        MarkerInfoWindow(
            state = MarkerState(seller.position),
            icon = sellerIcon,
            onClick = {
                viewModel.onClickSellerMarker(seller)
            }
        )
    }
    if (state.isOpenDialogMarker) {
        SellerMarkerDialog(state.selectedSeller!!, onDismiss = { viewModel.setIsOpenDialogMarker(false) }, navController)
    }
}