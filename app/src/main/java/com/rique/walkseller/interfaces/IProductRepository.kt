package com.rique.walkseller.interfaces

import com.rique.walkseller.domain.Product

interface IProductRepository {
    fun getProductsBySellerId(sellerId: String): List<Product>
}
