package com.rique.walkseller.domain

import android.location.Location
import com.rique.walkseller.dto.ProductDto

data class Order(
    val productById: Map<String, ProductDto> = emptyMap(),
    val totalProductsQuantity: Int = 0,
    val totalOrderPrice: Double = 0.0,
    val customerLocation: Location? = null,
    val sellerId: String = ""
)

