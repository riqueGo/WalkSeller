package com.rique.walkseller.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rique.walkseller.R
import com.rique.walkseller.interfaces.ISellerRepository
import com.rique.walkseller.domain.Seller
import java.io.InputStreamReader

class MockSellerRepository(private val context: Context): ISellerRepository {
    override fun getSellers(): List<Seller> {
        val inputStream = context.resources.openRawResource(R.raw.mock_sellers)
        val reader = InputStreamReader(inputStream)

        val listType = object : TypeToken<List<Seller>>() {}.type
        return Gson().fromJson(reader, listType)
    }
}