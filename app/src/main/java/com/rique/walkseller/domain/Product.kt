package com.rique.walkseller.domain

data class Product(
    val name: String,
    val description: String,
    val price: Double,
    val urlImage: String,
    val isActive: Boolean
)
