package com.rique.walkseller.interfaces

import com.rique.walkseller.domain.Seller

interface ISellerRepository {
    fun getSellers(): List<Seller>
}