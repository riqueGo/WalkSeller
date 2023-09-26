package com.rique.walkseller.repository

import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.storage.FirebaseStorage
import com.rique.walkseller.domain.Seller
import com.rique.walkseller.interfaces.ISellerRepository
import com.rique.walkseller.utils.Constants.DB_TAG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class SellerRepository : ISellerRepository {

    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()
    private val sellersFlow = MutableStateFlow<Collection<Seller>>(emptyList())
    private val sellerCache = mutableMapOf<String, Seller>() // Cache to store seller information
    private var registration: ListenerRegistration? = null

    override fun startListener() {
        if (registration != null) {
            return
        }

        // Start the Firestore SnapshotListener
        registration = db.collection("sellers")
            .whereEqualTo("isActive", true)
            .addSnapshotListener { document, e ->
                if (e != null) {
                    Log.w(DB_TAG, e.message.toString())
                    return@addSnapshotListener
                }
                val sellersList = mutableListOf<Seller>()
                for (doc in document!!) {
                    val name = doc.getString("name") ?: ""
                    val description = doc.getString("description") ?: ""
                    val geopoint = doc.getGeoPoint("location")
                    val position = LatLng(geopoint?.latitude ?: 0.0, geopoint?.longitude ?: 0.0)
                    val phone = doc.getString("phone") ?: ""
                    val sellerId = doc.id

                    // Check if the seller is in the cache
                    val cachedSeller = sellerCache[sellerId]
                    if (cachedSeller != null) {
                        // If the seller is in the cache, update the position
                        cachedSeller.position = position
                    } else {
                        // If not in cache, create a new seller
                        val seller = Seller(
                            id = sellerId,
                            name = name,
                            description = description,
                            position = position,
                            phone = phone
                        )

                        val profileImageRef = storage.reference.child("sellers/$sellerId/profile.jpeg")
                        val coverImageRef = storage.reference.child("sellers/$sellerId/cover.jpeg")

                        profileImageRef.downloadUrl.addOnSuccessListener { uri ->
                            seller.profileImageURL = uri.toString()
                        }

                        coverImageRef.downloadUrl.addOnSuccessListener { uri ->
                            seller.coverImageURL = uri.toString()
                        }

                        sellersList.add(seller)
                    }
                }
                sellersFlow.value = sellersList

                // Update the cache with the latest data
                for (seller in sellersList) {
                    sellerCache[seller.id] = seller
                }
            }
    }

    override fun stopListener() {
        registration?.remove()
        registration = null
    }

    override fun getSellers(): Flow<Collection<Seller>> {
        return sellersFlow
    }
}
