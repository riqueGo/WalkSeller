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
    private var registration: ListenerRegistration? = null

    override fun startListener() {
        if (registration != null) {
            return
        }

        // Start the Firestore SnapshotListener
        registration = db.collection("sellers")
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
                    val seller = Seller(
                        id = doc.id,
                        name = name,
                        description = description,
                        position = position,
                        phone = phone
                    )

                    val profileImageRef = storage.reference.child("sellers/${doc.id}/profile.jpeg")
                    val coverImageRef = storage.reference.child("sellers/${doc.id}/cover.jpeg")

                    profileImageRef.downloadUrl.addOnSuccessListener { uri ->
                        seller.profileImageURL = uri.toString()
                    }

                    coverImageRef.downloadUrl.addOnSuccessListener { uri ->
                        seller.coverImageURL = uri.toString()
                    }

                    sellersList.add(seller)
                }
                sellersFlow.value = sellersList
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
