package com.rique.walkseller.repository

import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.FirebaseFirestore
import com.rique.walkseller.domain.Seller
import com.rique.walkseller.interfaces.ISellerRepository
import com.rique.walkseller.utils.Constants.DB_TAG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class SellerRepository : ISellerRepository {

    private val db = FirebaseFirestore.getInstance()

    override fun getSellers(): Flow<Collection<Seller>> {
        val sellersFlow = MutableStateFlow<Collection<Seller>>(emptyList())

        db.collection("sellers")
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
                    sellersList.add(
                        Seller(
                            id = doc.id,
                            name = name,
                            description = description,
                            position = position
                        )
                    )
                }
                sellersFlow.value = sellersList
            }
        return sellersFlow
    }
}
