package com.rique.walkseller.repository

import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.FirebaseFirestore
import com.rique.walkseller.domain.Seller
import com.rique.walkseller.interfaces.ISellerRepository
import kotlinx.coroutines.tasks.await

class SellerRepository : ISellerRepository {

    private val TAG = "SellerRepository"

    private val db = FirebaseFirestore.getInstance()
    private val sellersCollection = db.collection("users")

    // Listar vendedores
    override suspend fun getSellers(): List<Seller> {
        val sellersList = mutableListOf<Seller>()

        try {
            val querySnapshot = sellersCollection.get().await()

            for (document in querySnapshot.documents) {
                val id = document.id
                val name = document.getString("name") ?: ""
                val description = document.getString("description") ?: ""
                // You may need to adjust these field names based on your Firestore data structure
                val geopoint = document.getGeoPoint("position")
                val position = LatLng(geopoint!!.latitude, geopoint.longitude)

                val seller = Seller(id, position, name, description)
                sellersList.add(seller)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error occured: " + e.message)
        }

        return sellersList
    }
}