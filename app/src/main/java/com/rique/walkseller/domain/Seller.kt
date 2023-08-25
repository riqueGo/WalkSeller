package com.rique.walkseller.domain

import com.google.android.gms.maps.model.LatLng

data class Seller(
    val id: String,
    val position: LatLng,
    val title: String,
    val description: String
)
