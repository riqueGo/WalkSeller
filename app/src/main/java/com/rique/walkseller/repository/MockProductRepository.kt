package com.rique.walkseller.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rique.walkseller.R
import com.rique.walkseller.domain.Product
import com.rique.walkseller.interfaces.IProductRepository
import java.io.InputStreamReader

class MockProductRepository(private val context: Context) : IProductRepository {
    override fun getProductsBySellerId(sellerId: String): List<Product> {
        val inputStream = context.resources.openRawResource(R.raw.mock_products)
        val reader = InputStreamReader(inputStream)

        val listType = object : TypeToken<List<Product>>() {}.type
        return Gson().fromJson(reader, listType)
    }
}