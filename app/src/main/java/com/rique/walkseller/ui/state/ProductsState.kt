package com.rique.walkseller.ui.state

import com.rique.walkseller.domain.Product

data class ProductsState(
    val products: List<Product> = emptyList()
)

