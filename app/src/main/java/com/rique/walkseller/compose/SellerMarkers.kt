package com.rique.walkseller.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState
import com.rique.walkseller.R
import com.rique.walkseller.Utils.Utils
import com.rique.walkseller.interfaces.ISellerRepository
import com.rique.walkseller.uiState.Seller

@Composable
fun SellerMarkers(repository: ISellerRepository) {
    val sellers = repository.getSellers()
    val bitmapDescriptor =
        Utils.bitmapDescriptorFromVector(LocalContext.current, R.drawable.delivery_dining)
    val selectedSeller = remember { mutableStateOf<Seller?>(null) }
    var openDialog by remember {
        mutableStateOf(false)
    }
    sellers.forEach { seller ->

        MarkerInfoWindow(
            state = MarkerState(seller.position),
            icon = bitmapDescriptor,
            onClick = {
                selectedSeller.value = seller
                openDialog = !openDialog
                true
            }
        )
    }
    if (openDialog) {
        SellerMarkerDialog(seller = selectedSeller, onDismiss = { openDialog = !openDialog })
    }
}