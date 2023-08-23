package com.rique.walkseller.ui.compose

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.rique.walkseller.R
import com.rique.walkseller.domain.Seller
import com.rique.walkseller.ui.viewModel.MapViewModel

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

@Composable
fun SellerBottomSheetCard(seller: Seller, onClick: () -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.product),
                contentDescription = seller.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = seller.title, fontWeight = FontWeight.Bold, fontSize = 16.sp
                )
                Text(text = seller.description)
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = {
                onClick()
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = stringResource(R.string.go_to_seller)
                )
            }
        }
    }
}

@Composable
fun SellerBottomSheetContent(viewModel: MapViewModel) {
    val mapState = viewModel.mapState.value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        SellerBottomSheetTitle(modifier = Modifier.align(Alignment.CenterHorizontally))
        mapState.sellers.forEach { seller ->
            SellerBottomSheetCard(seller = seller) { viewModel.onClickSellerBottomSheet(seller = seller) }
        }
    }
}
