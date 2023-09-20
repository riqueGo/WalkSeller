package com.rique.walkseller.ui.viewModel

import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.rique.walkseller.domain.Order
import com.rique.walkseller.domain.Product
import com.rique.walkseller.dto.ProductDto
import com.rique.walkseller.ui.state.OrderBottomSheetState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor() : ViewModel() {
    private val _orderState = mutableStateOf(Order())
    private val _orderBottomSheetState = mutableStateOf(OrderBottomSheetState())

    val orderState: State<Order>
        get() = _orderState

    val orderBottomSheetState: State<OrderBottomSheetState>
        get() = _orderBottomSheetState

    fun setInitialData(sellerId: String, sellerPhone: String, customerLocation: Location?) {
        _orderState.value = _orderState.value.copy(
            sellerId = sellerId,
            sellerPhone = sellerPhone,
            customerLocation = customerLocation
        )
    }

    fun setIsOpenOrderDetails(isOpen: Boolean) {
        _orderBottomSheetState.value =
            _orderBottomSheetState.value.copy(isOpenOrderDetails = isOpen)
    }

    private fun setOrderValues(
        productById: Map<String, ProductDto>,
        totalProductsQuantity: Int,
        totalOrderPrice: Double
    ) {
        _orderState.value = _orderState.value.copy(
            productById = productById,
            totalProductsQuantity = totalProductsQuantity,
            totalOrderPrice = totalOrderPrice
        )
    }

    fun addProduct(product: Product) {
        val existingProduct = _orderState.value.productById[product.id]

        val updatedProductDto =
            existingProduct?.copy(
                totalPrice = existingProduct.totalPrice + product.price,
                quantity = existingProduct.quantity + 1
            )
                ?: ProductDto(
                    id = product.id,
                    name = product.name,
                    totalPrice = product.price,
                    quantity = 1
                )

        setOrderValues(
            productById = _orderState.value.productById + (product.id to updatedProductDto),
            totalProductsQuantity = _orderState.value.totalProductsQuantity + 1,
            totalOrderPrice = _orderState.value.totalOrderPrice + product.price
        )
    }

    fun subtractProduct(product: Product) {
        val currentProductDto = _orderState.value.productById[product.id]
            ?: return // Product not in the order

        val updatedQuantity = currentProductDto.quantity - 1
        val updatedProductDto = currentProductDto.copy(
            quantity = updatedQuantity,
            totalPrice = product.price * updatedQuantity
        )

        val updatedProductById = if (updatedQuantity <= 0) {
            _orderState.value.productById - product.id
        } else {
            _orderState.value.productById + (product.id to updatedProductDto)
        }

        setOrderValues(
            productById = updatedProductById,
            totalProductsQuantity = _orderState.value.totalProductsQuantity - 1,
            totalOrderPrice = _orderState.value.totalOrderPrice - product.price
        )
    }

    fun getFormattedTotalPrice(): String {
        val orderState = _orderState.value
        return String.format("%.2f", orderState.totalOrderPrice)
    }

    fun getTotalProductsQuantityAsString(): String {
        val orderState = _orderState.value
        return orderState.totalProductsQuantity.toString()
    }

    // Function to get the customer's address from their location
    private fun getAddressFromLocation(context: Context, location: Location): String {
        val geocoder = Geocoder(context, Locale.getDefault())
        return try {
            val addresses = geocoder.getFromLocation(
                location.latitude,
                location.longitude,
                1 // Limit the number of results to 1
            )
            if (addresses?.isNotEmpty() == true) {
                return addresses[0].getAddressLine(0) // Get the first address (usually the most accurate)
            }
            return ""
        } catch (e: Exception) {
            "Error getting address: ${e.message}"
        }
    }

    private fun createOrderMessage(customerAddress: String, latitude: Double, longitude: Double): String {
        val googleMapsUrl = "https://www.google.com/maps/search/?api=1&query=$latitude,$longitude"
        val address = if(customerAddress.isNotBlank()) {
            "*Endereço:* $customerAddress\n\n"
        } else {
            ""
        }

        var message =
            "TO PASSANDO\n\n" +
                    "Estou nos arredores do endereço:\n\n" +
                    address +
                    "*Google Maps:* ${Uri.encode(googleMapsUrl)}\n\n" +
                    "*Detalhes do Pedido:*\n\n"

        _orderState.value.productById.values.forEach { productDto ->
            message += "${productDto.quantity}x ${productDto.name} - R$${productDto.totalPrice}\n"
        }
        message += "\n*Preço Total: R$${_orderState.value.totalOrderPrice}*\n"
        message += "_Itens Total: ${_orderState.value.totalProductsQuantity}_"
        return message
    }


    fun sendOrder(context: Context) {
        // Ensure that the customer's location is not null
        val customerLocation = _orderState.value.customerLocation
        if (customerLocation != null) {
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    // Get the customer's address from the location
                    val customerAddress = getAddressFromLocation(context, customerLocation)

                    val phoneNumber = _orderState.value.sellerPhone // recipient's phone number
                    val message =
                        createOrderMessage(customerAddress, customerLocation.latitude, customerLocation.longitude)

                    // Create a deep link to open WhatsApp with the specified message
                    val uri =
                        Uri.parse("https://api.whatsapp.com/send?phone=$phoneNumber&text=$message")

                    // Create an intent to open the link
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    context.startActivity(intent)
                } catch (e: Exception) {
                    // Handle any exceptions that may occur
                    launch(Dispatchers.Main) {
                        Toast.makeText(
                            context,
                            "Error sending order: ${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        } else {
            // Handle the case where customerLocation is null
            Toast.makeText(context, "Customer location is null", Toast.LENGTH_SHORT).show()
        }
    }
}