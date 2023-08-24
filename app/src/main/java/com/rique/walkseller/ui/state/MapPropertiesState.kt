package com.rique.walkseller.ui.state

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings

data class MapPropertiesState @OptIn(ExperimentalMaterial3Api::class) constructor(
    val isMapPropertiesLoaded: Boolean,
    val cameraPositionState: CameraPositionState,
    val sheetState: SheetState,
    val mapProperties: MapProperties?,
    val mapUiSettings: MapUiSettings
)
