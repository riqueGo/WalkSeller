package com.rique.walkseller.interfaces

import com.rique.walkseller.domain.Seller

interface ISellerRepository {
    suspend fun getSellers(): List<Seller>
}