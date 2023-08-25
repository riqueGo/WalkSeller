package com.rique.walkseller.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.maps.android.compose.GoogleMap
import com.rique.walkseller.R
import com.rique.walkseller.ui.compose.CustomFloatingActionButton
import com.rique.walkseller.ui.compose.SellerBottomSheetContent
import com.rique.walkseller.ui.compose.SellerMarkers
import com.rique.walkseller.ui.viewModel.MapViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(viewModel: MapViewModel) {
    val mapState = viewModel.mapState.value
    val mapPropertiesState = viewModel.mapPropertiesState.value
    val sellerMarkersViewModel = viewModel.getSellerMarkersViewModel()

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
                onMapLoaded = {
                    viewModel.moveToLocation(mapState.lastKnownLocation)
                }
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
                        viewModel.setIsOpenBottomSheet(true)
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
        if (mapState.isOpenBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    viewModel.setIsOpenBottomSheet(false)
                },
                sheetState = mapPropertiesState.sheetState
            ) {
                SellerBottomSheetContent(sellers = mapState.sellers, onClickSellerBottomSheet = viewModel::onClickSellerBottomSheet)
            }
        }
    }
}