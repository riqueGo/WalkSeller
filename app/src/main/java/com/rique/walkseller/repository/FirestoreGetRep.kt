import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.rique.walkseller.uiState.Seller

class FirestoreGetRepository : ISellerRepository {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    
    // Listar vendedores
    fun getSellers(): List<Seller> {
    val sellersList = mutableListOf<Seller>()
    
    val currentUser = auth.currentUser
    if (currentUser != null) {
        val userUid = currentUser.uid
        
        db.collection("users")
            .whereEqualTo("isSeller", true)
            .get()
            .addOnSuccessListener { querySnapshot: QuerySnapshot? ->
                querySnapshot?.forEach { documentSnapshot ->
                    val data = documentSnapshot.data
                    val isAvailable = data["isAvailable"] as Boolean
                    val location = data["location"] as GeoPoint
                    
                    val seller = Seller(documentSnapshot.id, isAvailable, location)
                    sellersList.add(seller)
                }
            }
            .addOnFailureListener { exception ->
                // Trate os erros aqui
            }
    }
    return sellersList
}

    // listar pedidos
       fun getOrders(): List<Order> {
        val ordersList = mutableListOf<Order>()
        val currentUser = auth.currentUser

        if (currentUser != null) {
            val userUid = currentUser.uid

            db.collection("orders")
                .whereEqualTo("sellerUid", userUid) 
                .get()
                .addOnSuccessListener { querySnapshot: QuerySnapshot? ->
                    querySnapshot?.forEach { documentSnapshot ->
                        val data = documentSnapshot.data
                        val buyerUid = data["buyerUid"] as String
                        val status = data["status"] as String
                        val order = Order(documentSnapshot.id, buyerUid, userUid, status)
                        ordersList.add(order)
                    }
        }
        return ordersList
    }

    // listar produtos
    fun getProducts(): List<Product> {
        val productsList = mutableListOf<Product>()

        val currentUser = auth.currentUser
        if (currentUser != null) {
            val userUid = currentUser.uid
            db.collection("products")
                .whereEqualTo("uid", userUid) 
                .get()
                .addOnSuccessListener { querySnapshot: QuerySnapshot? ->
                    querySnapshot?.forEach { documentSnapshot ->
                        val data = documentSnapshot.data
                        val imgUrl = data["imgUrl"] as String
                        val price = data["price"] as Double
                        val quantity = data["quantity"] as Int
                        val title = data["title"] as String
                        val product = Product(documentSnapshot.id, imgUrl, price, quantity, title, userUid)
                        productsList.add(product)
                    }
                }
        }
        return productsList
    }
}