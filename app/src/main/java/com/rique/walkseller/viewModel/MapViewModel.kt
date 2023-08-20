package com.rique.walkseller.viewModel

import android.annotation.SuppressLint
import android.location.Location
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.rique.walkseller.Utils.Constants.TAG
import com.rique.walkseller.Utils.Constants.TARGET_ZOOM
import com.rique.walkseller.interfaces.ISellerRepository
import com.rique.walkseller.uiState.MapState
import com.rique.walkseller.uiState.Seller
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val sellerRepository: ISellerRepository
) : ViewModel() {

    private val _state: MutableState<MapState> = mutableStateOf(
        MapState(
            lastKnownLocation = null,
            selectedSeller = null,
            sellers = emptyList(),
            isOpenDialogMarker = false
        )
    )

    val state: State<MapState>
        get() = _state

    fun setLastKnownLocation(location: Location) {
        _state.value = _state.value.copy(lastKnownLocation = location)
    }

    fun setSelectedSeller(seller: Seller) {
        _state.value = _state.value.copy(selectedSeller = seller)
    }

    fun setSellers(sellers: List<Seller>) {
        _state.value = _state.value.copy(sellers = sellers)
    }

    fun setIsOpenDialogMarker(isOpenDialog: Boolean){
        _state.value = _state.value.copy(isOpenDialogMarker = isOpenDialog)
    }

    fun loadSellers() {
        val sellers = sellerRepository.getSellers()
        setSellers(sellers)
    }

    fun onClickSellerMarker(seller: Seller): Boolean {
        setSelectedSeller(seller)
        setIsOpenDialogMarker(true)
        return true
    }

    @SuppressLint("MissingPermission")
    fun getDeviceLocation(fusedLocationProviderClient: FusedLocationProviderClient) {
        try {
            val locationResult = fusedLocationProviderClient.lastLocation
            locationResult.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    setLastKnownLocation(task.result)
                }
            }
        } catch (e: SecurityException) {
            Log.e(TAG, e.message.toString())
        }
    }

    private fun calculateCameraUpdate(location: LatLng, zoom: Float): CameraUpdate {
        val cameraPosition = CameraPosition.Builder()
            .target(location)
            .zoom(zoom)
            .build()
        return CameraUpdateFactory.newCameraPosition(cameraPosition)
    }

    fun moveToLocation(location: Location?, cameraPositionState: CameraPositionState) {
        location?.let {
            val cameraUpdate = calculateCameraUpdate(it.toLatLng(), TARGET_ZOOM)
            viewModelScope.launch {
                cameraPositionState.animate(cameraUpdate, 500)
            }
        }
    }

    private fun Location.toLatLng(): LatLng = LatLng(latitude, longitude)
}