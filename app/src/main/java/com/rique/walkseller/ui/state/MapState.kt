package com.rique.walkseller.ui.state

import android.location.Location
import com.rique.walkseller.domain.Seller

data class MapState(
    val lastKnownLocation: Location? = null,
    val selectedSeller: Seller? = null,
    val sellers: List<Seller> = emptyList(),
    val isOpenDialogMarker: Boolean = false,
    val isOpenBottomSheet: Boolean = false
)
