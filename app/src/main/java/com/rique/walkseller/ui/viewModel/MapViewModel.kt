package com.rique.walkseller.ui.viewModel

import android.content.Context
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
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.MapProperties
import com.rique.walkseller.R
import com.rique.walkseller.utils.Constants.APP_TAG
import com.rique.walkseller.interfaces.ISellerRepository
import com.rique.walkseller.ui.state.MapState
import com.rique.walkseller.domain.Seller
import com.rique.walkseller.ui.state.MapPropertiesState
import com.rique.walkseller.utils.Constants.DURATION_POS_CAMERA
import com.rique.walkseller.utils.Constants.MAX_ZOOM
import com.rique.walkseller.utils.Constants.MIN_ZOOM
import com.rique.walkseller.utils.Constants.TARGET_ZOOM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

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

    private fun setLastKnownLocation(location: Location) {
        _mapState.value = _mapState.value.copy(lastKnownLocation = location)
    }

    private fun setSellers(sellers: Flow<Collection<Seller>>) {
        _mapState.value = _mapState.value.copy(sellers = sellers)
    }

    private fun setIsMapPropertiesLoaded(isLoaded: Boolean) {
        _mapPropertiesState.value = _mapPropertiesState.value.copy(isMapPropertiesLoaded = isLoaded)
    }

    private fun setMapProperties(context: Context) {
        _mapPropertiesState.value = _mapPropertiesState.value.copy(
            mapProperties = MapProperties(
                isMyLocationEnabled = true,
                mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context, R.raw.map),
                maxZoomPreference = MAX_ZOOM,
                minZoomPreference = MIN_ZOOM
            )
        )
    }

    private fun loadSellers() {
        sellerRepository.startListener()
        val sellers = sellerRepository.getSellers().flowOn(Dispatchers.IO)
        setSellers(sellers)
    }

    fun initMap(fusedLocationProviderClient: FusedLocationProviderClient, context: Context) {
        if (!_mapPropertiesState.value.isMapPropertiesLoaded) {
            setMapProperties(context)
            loadSellers()
            setIsMapPropertiesLoaded(true)
            setDeviceLocation(fusedLocationProviderClient)
        }
    }

    fun closeMap() {
        sellerRepository.stopListener()
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
            Log.e(APP_TAG, e.message.toString())
        }
    }

    private fun calculateCameraUpdate(location: LatLng): CameraUpdate {
        val cameraPosition = CameraPosition.Builder()
            .target(location)
            .zoom(TARGET_ZOOM)
            .build()
        return CameraUpdateFactory.newCameraPosition(cameraPosition)
    }

    fun moveToLocation(location: Location?, onCompletion: () -> Unit = {}) {
        location?.let {
            val cameraUpdate = calculateCameraUpdate(it.toLatLng())
            viewModelScope.launch {
                _mapPropertiesState.value.cameraPositionState.animate(
                    cameraUpdate,
                    DURATION_POS_CAMERA
                )
            }.invokeOnCompletion { onCompletion() }
        }
    }

    fun moveToLocation(location: LatLng, onCompletion: () -> Unit = {}) {
        val cameraUpdate = calculateCameraUpdate(location)
        viewModelScope.launch {
            _mapPropertiesState.value.cameraPositionState.animate(cameraUpdate, DURATION_POS_CAMERA)
        }.invokeOnCompletion { onCompletion() }
    }

    private fun Location.toLatLng(): LatLng = LatLng(latitude, longitude)
}