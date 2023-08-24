package com.rique.walkseller.interfaces

import com.rique.walkseller.domain.Product
import java.util.UUID

interface IProductRepository {
    fun getProductsBySellerId(sellerId: UUID): List<Product>
}
