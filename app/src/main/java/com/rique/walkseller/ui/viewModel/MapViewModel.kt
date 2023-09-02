package com.rique.walkseller.ui.viewModel

import android.content.Context
import android.location.Location
import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.MapProperties
import com.rique.walkseller.R
import com.rique.walkseller.utils.Constants
import com.rique.walkseller.utils.Constants.TAG
import com.rique.walkseller.utils.Constants.TARGET_ZOOM
import com.rique.walkseller.interfaces.ISellerRepository
import com.rique.walkseller.ui.state.MapState
import com.rique.walkseller.domain.Seller
import com.rique.walkseller.ui.state.MapPropertiesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@HiltViewModel
class MapViewModel @Inject constructor(
    private val sellerRepository: ISellerRepository,
) : ViewModel() {

    private val _mapPropertiesState: MutableState<MapPropertiesState> = mutableStateOf(
        MapPropertiesState()
    )

    val mapPropertiesState: State<MapPropertiesState>
        get() = _mapPropertiesState

    private val _mapState: MutableState<MapState> = mutableStateOf(
        MapState()
    )

    val mapState: State<MapState>
        get() = _mapState

    fun setLastKnownLocation(location: Location) {
        _mapState.value = _mapState.value.copy(lastKnownLocation = location)
    }

    fun setSellers(sellers: List<Seller>) {
        _mapState.value = _mapState.value.copy(sellers = sellers)
    }

    fun setIsMapPropertiesLoaded(isLoaded: Boolean) {
        _mapPropertiesState.value = _mapPropertiesState.value.copy(isMapPropertiesLoaded = isLoaded)
    }

    private fun setMapProperties(context: Context) {
        _mapPropertiesState.value = _mapPropertiesState.value.copy(
            mapProperties = MapProperties(
                isMyLocationEnabled = true,
                mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context, R.raw.map),
                maxZoomPreference = Constants.MAX_ZOOM,
                minZoomPreference = Constants.MIN_ZOOM
            )
        )
    }

    private fun loadSellers() {
        viewModelScope.launch {
            while (true) {
                val sellers = sellerRepository.getSellers()
                setSellers(sellers)
                kotlinx.coroutines.delay(20000)
            }
        }
    }

    fun initMap(fusedLocationProviderClient: FusedLocationProviderClient, context: Context) {
        if (!_mapPropertiesState.value.isMapPropertiesLoaded) {
            setMapProperties(context)
            loadSellers()
            setIsMapPropertiesLoaded(true)
            setDeviceLocation(fusedLocationProviderClient)
        }
    }

    private fun setDeviceLocation(fusedLocationProviderClient: FusedLocationProviderClient) {
        try {
            val locationResult = fusedLocationProviderClient.lastLocation
            locationResult.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    setLastKnownLocation(task.result)
                    moveToLocation(task.result)
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

    fun moveToLocation(location: Location?, onCompletion: () -> Unit = {}) {
        location?.let {
            val cameraUpdate = calculateCameraUpdate(it.toLatLng(), TARGET_ZOOM)
            viewModelScope.launch {
                _mapPropertiesState.value.cameraPositionState.animate(cameraUpdate, 500)
            }.invokeOnCompletion { onCompletion() }
        }
    }

    fun moveToLocation(location: LatLng, onCompletion: () -> Unit = {}) {
        val cameraUpdate = calculateCameraUpdate(location, TARGET_ZOOM)
        viewModelScope.launch {
            _mapPropertiesState.value.cameraPositionState.animate(cameraUpdate, 500)
        }.invokeOnCompletion { onCompletion() }
    }

    private fun Location.toLatLng(): LatLng = LatLng(latitude, longitude)
}