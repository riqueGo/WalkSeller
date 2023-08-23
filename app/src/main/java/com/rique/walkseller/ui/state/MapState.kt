package com.rique.walkseller.ui.state

import android.location.Location
import com.rique.walkseller.domain.Seller

data class MapState(
    val lastKnownLocation: Location?,
    val selectedSeller: Seller?,
    val sellers: List<Seller>,
    val isOpenDialogMarker: Boolean,
    val isOpenBottomSheet: Boolean
)
