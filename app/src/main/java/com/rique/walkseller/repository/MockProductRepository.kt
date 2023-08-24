package com.rique.walkseller.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rique.walkseller.domain.Product
import com.rique.walkseller.interfaces.IProductRepository
import java.util.UUID

class MockProductRepository(private val context: Context) : IProductRepository {
    override fun getProductsBySellerId(sellerId: UUID): List<Product> {
        val json = context.assets.open("mock_products.json").bufferedReader().use {
            it.readText()
        }
        return Gson().fromJson(json, object : TypeToken<List<Product>>() {}.type)
    }
}