package com.rique.walkseller.ui.state

import android.location.Location
import com.rique.walkseller.domain.Seller

data class MapState(
    val lastKnownLocation: Location? = null,
    val sellers: List<Seller> = emptyList()
)
