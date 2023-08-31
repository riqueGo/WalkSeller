package com.rique.walkseller.ui.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.rique.walkseller.interfaces.IProductRepository
import com.rique.walkseller.ui.state.ProductsState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val productRepository: IProductRepository
) : ViewModel() {

    private val _productsState = mutableStateOf(ProductsState())
    val productsState: State<ProductsState>
        get() = _productsState

    fun loadProducts(sellerId: String) {
        val products = productRepository.getProductsBySellerId(sellerId)
        _productsState.value = _productsState.value.copy(products = products)
    }
}
