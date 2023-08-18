package com.rique.walkseller.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import com.rique.walkseller.R
import com.rique.walkseller.viewModel.MapViewModel

@Composable
fun MapScreen(viewModel: MapViewModel) {
    val state = viewModel.state.value
    val cameraPositionState = rememberCameraPositionState()

    val mapProperties = MapProperties(
        isMyLocationEnabled = state.lastKnownLocation != null,
        mapStyleOptions = MapStyleOptions.loadRawResourceStyle(LocalContext.current, R.raw.map)
    )

    val mapUiSettings = MapUiSettings(
        myLocationButtonEnabled = false,
        zoomControlsEnabled = false,
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            properties = mapProperties,
            cameraPositionState = cameraPositionState,
            uiSettings = mapUiSettings,
            onMapLoaded = {
                viewModel.moveToLocation(state.lastKnownLocation, cameraPositionState)
            }
        )
        Column(modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(16.dp)) {
            FloatingActionButton(
                onClick = {
                          //TODO: Show all sellers
                },
            ) {
                Icon(painter = painterResource(id = R.drawable.shopping_basket), contentDescription = "Sellers")
            }
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            FloatingActionButton(
                onClick = {
                    viewModel.moveToLocation(state.lastKnownLocation, cameraPositionState)
                },
            ) {
                Icon(painter = painterResource(id = R.drawable.my_location), contentDescription = "My Location")
            }
        }

    }
}