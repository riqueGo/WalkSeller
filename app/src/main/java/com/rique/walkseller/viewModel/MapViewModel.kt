package com.rique.walkseller.viewModel

import android.annotation.SuppressLint
import android.location.Location
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.rique.walkseller.uiState.MapState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(): ViewModel() {
    private val TAG: String = "MapViewModel"
    private val TARGET_ZOOM: Float = 18f
    val state: MutableState<MapState> = mutableStateOf(
        MapState(lastKnownLocation = null)
    )

    @SuppressLint("MissingPermission")
    fun getDeviceLocation(fusedLocationProviderClient: FusedLocationProviderClient) {
        try {
            val locationResult = fusedLocationProviderClient.lastLocation
            locationResult.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    state.value = state.value.copy(
                        lastKnownLocation = task.result
                    )
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
                cameraPositionState.animate(cameraUpdate)
            }
        }
    }

    private fun Location.toLatLng(): LatLng = LatLng(latitude, longitude)
}