package com.rique.walkseller.ui.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.rique.walkseller.domain.Seller
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

    fun initSellerMarkerState(
        sellers: List<Seller>,
        isOpenDialogMarker: Boolean,
        setIsOpenDialogMarker: (Boolean) -> Unit,
        moveToLocation: (LatLng) -> Unit
    ) {
        _sellerMarkersState.value = _sellerMarkersState.value.copy(
            sellers = sellers,
            isOpenDialogMarker = isOpenDialogMarker,
            setIsOpenDialogMarker = setIsOpenDialogMarker,
            moveToLocation = moveToLocation
        )
    }

    fun setSelectedSeller(seller: Seller) {
        _sellerMarkersState.value = _sellerMarkersState.value.copy(selectedSeller = seller)
    }

    fun setIsOpenDialogMarker(isOpen: Boolean){
        _sellerMarkersState.value = _sellerMarkersState.value.copy(isOpenDialogMarker = isOpen)
        _sellerMarkersState.value.setIsOpenDialogMarker(isOpen)
    }

    fun onClickSellerMarker(seller: Seller) : Boolean {
        _sellerMarkersState.value.moveToLocation(seller.position)
        setSelectedSeller(seller)
        setIsOpenDialogMarker(true)
        return true
    }
}