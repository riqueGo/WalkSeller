package com.rique.walkseller.repository

import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.rique.walkseller.interfaces.ISellerRepository
import com.rique.walkseller.uiState.Seller

class SellerRepository : ISellerRepository {

    private val TAG = "SellerRepository"

    private val db = Firebase.firestore

    // Listar vendedores
    override fun getSellers(): List<Seller> {
        val sellersList = mutableListOf<Seller>()

        db.collection("users")
            .whereEqualTo("isSeller", true)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val geopoint = document.getGeoPoint("location")
                    val latlng = LatLng(geopoint!!.latitude, geopoint.longitude)
                    val seller = Seller(
                        document.id,
                        latlng,
                        document.data["name"] as String,
                        document.data["description"] as String
                    )
                    sellersList.add(seller)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting seller: ", exception)
            }

        return sellersList
    }



//    // listar pedidos
//    fun getOrders(): List<Order> {
//        val ordersList = mutableListOf<Order>()
//        val currentUser = auth.currentUser
//
//        if (currentUser != null) {
//            val userUid = currentUser.uid
//
//            db.collection("orders")
//                .whereEqualTo("sellerUid", userUid)
//                .get()
//                .addOnSuccessListener { querySnapshot: QuerySnapshot? ->
//                    querySnapshot?.forEach { documentSnapshot ->
//                        val data = documentSnapshot.data
//                        val buyerUid = data["buyerUid"] as String
//                        val status = data["status"] as String
//                        val order = Order(documentSnapshot.id, buyerUid, userUid, status)
//                        ordersList.add(order)
//                    }
//                }
//            return ordersList
//        }
//
//        // listar produtos
//        fun getProducts(): List<Product> {
//            val productsList = mutableListOf<Product>()
//
//            val currentUser = auth.currentUser
//            if (currentUser != null) {
//                val userUid = currentUser.uid
//                db.collection("products")
//                    .whereEqualTo("uid", userUid)
//                    .get()
//                    .addOnSuccessListener { querySnapshot: QuerySnapshot? ->
//                        querySnapshot?.forEach { documentSnapshot ->
//                            val data = documentSnapshot.data
//                            val imgUrl = data["imgUrl"] as String
//                            val price = data["price"] as Double
//                            val quantity = data["quantity"] as Int
//                            val title = data["title"] as String
//                            val product = Product(
//                                documentSnapshot.id,
//                                imgUrl,
//                                price,
//                                quantity,
//                                title,
//                                userUid
//                            )
//                            productsList.add(product)
//                        }
//                    }
//            }
//            return productsList
//        }

}