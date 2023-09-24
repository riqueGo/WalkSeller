package com.rique.walkseller.domain

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    var urlImage: String = "",
    val isActive: Boolean
)
