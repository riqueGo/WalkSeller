package com.rique.walkseller.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.rique.walkseller.R
import com.rique.walkseller.ui.compose.CustomFloatingActionButton
import com.rique.walkseller.ui.compose.SellerBottomSheet
import com.rique.walkseller.ui.compose.SellerMarkers
import com.rique.walkseller.ui.viewModel.MapViewModel
import com.rique.walkseller.ui.viewModel.SellerBottomSheetViewModel
import com.rique.walkseller.ui.viewModel.SellerMarkersViewModel

@Composable
fun MapScreen(viewModel: MapViewModel) {
    val mapState = viewModel.mapState.value
    val mapPropertiesState = viewModel.mapPropertiesState.value

    val sellerMarkersViewModel: SellerMarkersViewModel = hiltViewModel()
    val sellers = mapState.sellers.collectAsState(initial = emptyList()).value

    sellerMarkersViewModel.setInitialData(
        sellers = sellers
    ) { position: LatLng, onCompletion: () -> Unit ->
        viewModel.moveToLocation(
            position,
            onCompletion
        )
    }

    val sellerBottomSheetViewModel: SellerBottomSheetViewModel = hiltViewModel()
    sellerBottomSheetViewModel.setInitialData(sellers, sellerMarkersViewModel::onClickSellerMarker)

    Scaffold { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                properties = mapPropertiesState.mapProperties!!,
                cameraPositionState = mapPropertiesState.cameraPositionState,
                uiSettings = mapPropertiesState.mapUiSettings,
            ) {
                SellerMarkers(sellerMarkersViewModel)
            }
            Column(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CustomFloatingActionButton(
                    onClick = {
                        sellerBottomSheetViewModel.setIsOpenBottomSheet(true)
                    },
                    drawableResId = R.drawable.shopping_basket,
                    contentDescription = stringResource(id = R.string.sellers)
                )
                CustomFloatingActionButton(
                    onClick = {
                        viewModel.moveToLocation(mapState.lastKnownLocation)
                    },
                    drawableResId = R.drawable.my_location,
                    contentDescription = stringResource(id = R.string.my_location)
                )
            }
        }
        SellerBottomSheet(viewModel = sellerBottomSheetViewModel)
    }
}