package com.rique.walkseller.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.rique.walkseller.domain.Product
import com.rique.walkseller.interfaces.IProductRepository
import com.rique.walkseller.utils.Constants.DB_TAG
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class ProductRepository : IProductRepository {
    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

    // Create a cache using a map
    private val productCache = mutableMapOf<String, List<Product>>()

    override suspend fun getProductsBySellerId(sellerId: String): List<Product> {
        // Check if the result is already cached
        if (productCache.containsKey(sellerId)) {
            return productCache[sellerId]!!
        }

        return suspendCoroutine { continuation ->
            // If not in the cache, fetch the data from Firestore
            val products = mutableListOf<Product>()

            db.collection("seller-products").document(sellerId).collection("products").get()
                .addOnSuccessListener { querySnapshot ->
                    for (document in querySnapshot.documents) {
                        val id = document.id
                        val name = document.getString("name") ?: ""
                        val description = document.getString("description") ?: ""
                        val price = document.getDouble("price") ?: 0.0
                        val isActive = document.getBoolean("isActive") ?: true

                        val product = Product(
                            id = id,
                            name = name,
                            description = description,
                            price = price,
                            isActive = isActive
                        )

                        val urlImageRef = storage.reference.child("sellers/${sellerId}/products/${document.id}.jpeg")

                        urlImageRef.downloadUrl.addOnSuccessListener { uri ->
                            product.urlImage = uri.toString()
                        }

                        products.add(product)
                    }
                    // Cache the result
                    productCache[sellerId] = products
                    continuation.resume(products)
                }.addOnFailureListener { exception ->
                    Log.w(DB_TAG, exception.message.toString())
                    continuation.resumeWithException(exception)
                }
        }
    }
}
