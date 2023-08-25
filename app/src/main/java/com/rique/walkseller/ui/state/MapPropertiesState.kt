package com.rique.walkseller.ui.state

import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings

data class MapPropertiesState(
    val isMapPropertiesLoaded: Boolean = false,
    val cameraPositionState: CameraPositionState = CameraPositionState(),
    val mapProperties: MapProperties? = null,
    val mapUiSettings: MapUiSettings = MapUiSettings(
        myLocationButtonEnabled = false,
        zoomControlsEnabled = false,
        mapToolbarEnabled = false
    )
)
