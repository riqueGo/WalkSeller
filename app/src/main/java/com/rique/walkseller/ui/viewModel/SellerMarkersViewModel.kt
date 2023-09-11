package com.rique.walkseller.ui.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.android.gms.maps.model.LatLng
import com.rique.walkseller.domain.Seller
import com.rique.walkseller.navigation.NavDestination
import com.rique.walkseller.ui.state.SellerMarkersState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SellerMarkersViewModel @Inject constructor() : ViewModel() {

    private val _sellerMarkersState: MutableState<SellerMarkersState> = mutableStateOf(
        SellerMarkersState()
    )

    val sellerMarkersState: State<SellerMarkersState>
        get() = _sellerMarkersState

    fun setInitialData(
        sellers: Collection<Seller>,
        moveToLocation: (LatLng, onCompletion: () -> Unit) -> Unit,
        closeMap: () -> Unit
    ) {
        _sellerMarkersState.value = _sellerMarkersState.value.copy(
            sellers = sellers,
            moveToLocation = moveToLocation,
            closeMap = closeMap
        )
    }

    fun setSelectedSeller(seller: Seller) {
        _sellerMarkersState.value = _sellerMarkersState.value.copy(selectedSeller = seller)
    }

    fun setIsOpenDialogMarker(isOpen: Boolean) {
        _sellerMarkersState.value = _sellerMarkersState.value.copy(isOpenDialogMarker = isOpen)
    }

    fun onClickSellerMarker(seller: Seller): Boolean {
        _sellerMarkersState.value.moveToLocation(seller.position) {
            setSelectedSeller(seller)
            setIsOpenDialogMarker(true)
        }
        return true
    }

    fun navigateToProducts(navController: NavController, seller: Seller) {
        setIsOpenDialogMarker(isOpen = false)
        _sellerMarkersState.value.closeMap()

        val route = NavDestination.ProductsScreen.createRoute(sellerId = seller.id)
        navController.currentBackStackEntry?.savedStateHandle?.apply { set("seller", seller) }
        navController.navigate(route = route)
    }
}