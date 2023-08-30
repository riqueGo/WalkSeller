package com.rique.walkseller.domain

import com.google.android.gms.maps.model.LatLng
import com.rique.walkseller.dto.ProductDto

data class Order(
    val productById: Map<String, ProductDto> = emptyMap(),
    val totalProductsQuantity: Int = 0,
    val totalOrderPrice: Double = 0.0,
    val customerLocation: LatLng = LatLng(0.0, 0.0),
    val sellerId: String = ""
)

