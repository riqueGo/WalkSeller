package com.rique.walkseller.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.rique.walkseller.domain.Product
import com.rique.walkseller.interfaces.IProductRepository
import com.rique.walkseller.utils.Constants.DB_TAG
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class ProductRepository : IProductRepository {
    private val db = FirebaseFirestore.getInstance()

    override suspend fun getProductsBySellerId(sellerId: String): List<Product> {
        return suspendCoroutine { continuation ->
            val products = mutableListOf<Product>()

            db.collection("seller-products")
                .document(sellerId)
                .collection("products")
                .get()
                .addOnSuccessListener { querySnapshot ->
                    for (document in querySnapshot.documents) {
                        val id = document.id
                        val name = document.getString("name") ?: ""
                        val description = document.getString("description") ?: ""
                        val price = document.getDouble("price") ?: 0.0
                        val urlImage = document.getString("urlImage") ?: ""
                        val isActive = document.getBoolean("isActive") ?: true

                        val product = Product(
                            id = id,
                            name = name,
                            description = description,
                            price = price,
                            urlImage = urlImage,
                            isActive = isActive
                        )

                        products.add(product)
                    }
                    continuation.resume(products)
                }
                .addOnFailureListener { exception ->
                    Log.w(DB_TAG, exception.message.toString())
                    continuation.resumeWithException(exception)
                }
        }
    }
}