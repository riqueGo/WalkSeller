package com.rique.walkseller.interfaces

import com.rique.walkseller.uiState.Seller

interface ISellerRepository {
    fun getSellers(): List<Seller>
}
