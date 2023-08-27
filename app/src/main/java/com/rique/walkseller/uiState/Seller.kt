package com.rique.walkseller.uiState

import com.google.android.gms.maps.model.LatLng
import java.util.UUID

data class Seller(
    val id: String,
    val position: LatLng,
    val title: String,
    val description: String
)
