package com.rique.walkseller.ui.state

import android.location.Location
import com.rique.walkseller.domain.Seller
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class MapState(
    val lastKnownLocation: Location? = null,
    val sellers: Flow<Collection<Seller>> = emptyFlow()
)
