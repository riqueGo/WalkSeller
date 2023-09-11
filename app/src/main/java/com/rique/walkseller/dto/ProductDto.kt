package com.rique.walkseller.dto

data class ProductDto(
    val id: String,
    val name: String,
    var totalPrice: Double = 0.0,
    var quantity: Int = 0
)
