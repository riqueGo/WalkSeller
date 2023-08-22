package com.rique.walkseller.domain

import com.google.android.gms.maps.model.LatLng
import java.util.UUID

data class Seller(
    val id: UUID,
    val position: LatLng,
    val title: String,
    val description: String
)