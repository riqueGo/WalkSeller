package com.rique.walkseller.domain

data class Product(
    val name: String,
    val description: String,
    val price: Double,
    val quantity : Int = 0,
    val urlImage: String,
    val isActive: Boolean
)
