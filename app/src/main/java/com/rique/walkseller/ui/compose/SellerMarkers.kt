package com.rique.walkseller.ui.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState
import com.rique.walkseller.di.LocalSellerMarkersViewModelProvider
import com.rique.walkseller.R
import com.rique.walkseller.utils.Utils

@Composable
fun SellerMarkers() {
    val sellerMarkersViewModel = LocalSellerMarkersViewModelProvider.current
    val state = sellerMarkersViewModel.sellerMarkersState.value
    val sellerIcon =
        Utils.bitmapDescriptorFromVector(LocalContext.current, R.drawable.delivery_dining)

    state.sellers.forEach { seller ->
        MarkerInfoWindow(
            state = MarkerState(seller.position),
            icon = sellerIcon,
            onClick = {
                sellerMarkersViewModel.onClickSellerMarker(seller)
            }
        )
    }
    if (state.isOpenDialogMarker) {
        SellerMarkerDialog(onDismiss = { sellerMarkersViewModel.setIsOpenDialogMarker(false) })
    }
}