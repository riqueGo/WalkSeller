package com.rique.walkseller.uiState

import android.location.Location

data class MapState(
    val lastKnownLocation: Location?,
    val selectedSeller: Seller?,
    val sellers: List<Seller>,
    val isOpenDialogMarker: Boolean
)
