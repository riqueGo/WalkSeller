package com.rique.walkseller.interfaces

import com.rique.walkseller.domain.Seller
import kotlinx.coroutines.flow.Flow

interface ISellerRepository {
    fun getSellers(): Flow<Collection<Seller>>
}