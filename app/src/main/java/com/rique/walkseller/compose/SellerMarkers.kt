package com.rique.walkseller.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.rique.walkseller.R
import com.rique.walkseller.Utils.Utils
import com.rique.walkseller.interfaces.ISellerRepository

@Composable
fun SellerMarkers(repository: ISellerRepository) {
    val sellers = repository.getSellers()
    val bitmapDescriptor = Utils.bitmapDescriptorFromVector(LocalContext.current, R.drawable.delivery_dining)

    sellers.forEach{ seller ->

        Marker(
            state = MarkerState(seller.position),
            title = seller.title,
            icon = bitmapDescriptor,
            snippet = seller.description
        )
    }
}