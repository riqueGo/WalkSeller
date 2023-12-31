package com.rique.walkseller.ui.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.rique.walkseller.R
import com.rique.walkseller.di.LocalNavControllerProvider
import com.rique.walkseller.domain.Seller
import com.rique.walkseller.ui.viewModel.SellerBottomSheetViewModel
import kotlinx.coroutines.launch

@Composable
fun SellerBottomSheetTitle(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.sellers), style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            letterSpacing = 0.15.sp,
            color = Color.Black
        ), modifier = modifier
            .padding(bottom = 8.dp)
    )

    Divider(color = Color.Gray, modifier = Modifier.padding(bottom = 16.dp))
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun SellerBottomSheetCard(
    seller: Seller,
    onClickPositionSeller: () -> Unit,
    onClickCard: () -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .clickable {
                onClickCard()
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            GlideImage(
                model = seller.profileImageURL,
                contentDescription = seller.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.weight(2f)
            ) {
                Text(
                    text = seller.name, fontWeight = FontWeight.Bold, fontSize = 16.sp
                )
                Text(text = seller.description)
            }
            Spacer(modifier = Modifier.width(16.dp))
            IconButton(
                onClick = {
                    onClickPositionSeller()
                },
                modifier = Modifier.weight(0.5f)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.my_location),
                    contentDescription = stringResource(R.string.go_to_seller)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellerBottomSheet(viewModel: SellerBottomSheetViewModel) {
    val state = viewModel.sellerBottomSheetState.value
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    val navController = LocalNavControllerProvider.current


    if (state.isOpenBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { viewModel.setIsOpenBottomSheet(false) },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                SellerBottomSheetTitle(modifier = Modifier.align(Alignment.CenterHorizontally))
                state.sellers.forEach { seller ->
                    SellerBottomSheetCard(
                        seller = seller,
                        onClickPositionSeller = {
                            scope.launch {
                                sheetState.hide()
                            }.invokeOnCompletion {
                                viewModel.onClickSellerBottomSheet(seller)
                            }
                        },
                        onClickCard = {
                            viewModel.navigateToProducts(
                                navController = navController,
                                seller = seller
                            )
                        }
                    )
                }
            }
        }
    }
}
