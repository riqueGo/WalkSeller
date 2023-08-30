package com.rique.walkseller.ui.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.rique.walkseller.domain.Order
import com.rique.walkseller.domain.Product
import com.rique.walkseller.dto.ProductDto
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor() : ViewModel() {
    private val _order = mutableStateOf(Order())

    val order: State<Order>
        get() = _order

    fun setInitalData(sellerId: String, customerLocation: LatLng) {
        _order.value = _order.value.copy(sellerId = sellerId, customerLocation = customerLocation)
    }

    private fun setOrderValues(
        productById: Map<String, ProductDto>,
        totalProductsQuantity: Int,
        totalOrderPrice: Double
    ) {
        _order.value = _order.value.copy(
            productById = productById,
            totalProductsQuantity = totalProductsQuantity,
            totalOrderPrice = totalOrderPrice
        )
    }

    fun addProduct(product: Product){
        val existingProduct = _order.value.productById[product.id]

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
            productById = _order.value.productById + (product.id to updatedProductDto),
            totalProductsQuantity = _order.value.totalProductsQuantity + 1,
            totalOrderPrice = _order.value.totalOrderPrice + product.price
        )
    }

    fun subtractProduct(product: Product) {
        val currentProductDto = _order.value.productById[product.id]
            ?: return // Product not in the order

        val updatedQuantity = currentProductDto.quantity - 1
        val updatedProductDto = currentProductDto.copy(
            quantity = updatedQuantity,
            totalPrice = product.price * updatedQuantity
        )

        val updatedProductById = if(updatedQuantity <= 0){
            _order.value.productById - product.id
        } else {
            _order.value.productById + (product.id to updatedProductDto)
        }

        setOrderValues(
            productById = updatedProductById,
            totalProductsQuantity = _order.value.totalProductsQuantity - 1,
            totalOrderPrice = _order.value.totalOrderPrice - product.price
        )
    }
}