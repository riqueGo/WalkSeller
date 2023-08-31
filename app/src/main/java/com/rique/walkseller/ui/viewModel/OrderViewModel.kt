package com.rique.walkseller.ui.viewModel

import android.location.Location
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.rique.walkseller.domain.Order
import com.rique.walkseller.domain.Product
import com.rique.walkseller.dto.ProductDto
import com.rique.walkseller.ui.state.OrderBottomSheetState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor() : ViewModel() {
    private val _orderState = mutableStateOf(Order())
    private val _orderBottomSheetState = mutableStateOf(OrderBottomSheetState())
    private val expandedOrderBottomSheetHeight = 96.dp

    val orderState: State<Order>
        get() = _orderState

    val orderBottomSheetState: State<OrderBottomSheetState>
        get() = _orderBottomSheetState

    fun setInitialData(sellerId: String, customerLocation: Location?) {
        _orderState.value = _orderState.value.copy(sellerId = sellerId, customerLocation = customerLocation)
    }

    fun setIsOpenOrderDetails(isOpen: Boolean){
        _orderBottomSheetState.value = _orderBottomSheetState.value.copy(isOpenOrderDetails = isOpen)
    }

    fun setSheetHeight(height: Dp){
        _orderBottomSheetState.value = _orderBottomSheetState.value.copy(sheetHeight = height)
    }

    fun getExpandedOrderBottomSheetHeight(): Dp {
        return (20 * _orderState.value.productById.size).dp + expandedOrderBottomSheetHeight
    }

    fun adjustSheetBottomOrderDetailHeight() {
        val expandedOrderDetailsHeight = getExpandedOrderBottomSheetHeight()

        if (_orderBottomSheetState.value.isOpenOrderDetails && _orderBottomSheetState.value.sheetHeight != expandedOrderDetailsHeight) {
            setSheetHeight(expandedOrderDetailsHeight)
        } else if (!_orderBottomSheetState.value.isOpenOrderDetails && _orderBottomSheetState.value.sheetHeight != expandedOrderBottomSheetHeight){
            setSheetHeight(expandedOrderBottomSheetHeight)
        }
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

    fun addProduct(product: Product){
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

        val updatedProductById = if(updatedQuantity <= 0){
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
}