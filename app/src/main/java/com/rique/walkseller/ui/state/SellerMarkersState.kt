package com.rique.walkseller.ui.state

import com.google.android.gms.maps.model.LatLng
import com.rique.walkseller.domain.Seller

data class SellerMarkersState(
    val selectedSeller: Seller? = null,
    val sellers: Collection<Seller> = emptyList(),
    val isOpenDialogMarker: Boolean = false,
    val setIsOpenDialogMarker: (Boolean) -> Unit = { },
    val moveToLocation: (LatLng, onCompletion: () -> Unit) -> Unit = { _, _ -> }
)
