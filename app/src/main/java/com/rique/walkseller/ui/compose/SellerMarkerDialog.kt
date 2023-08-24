package com.rique.walkseller.ui.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.rique.walkseller.R
import com.rique.walkseller.utils.Utils
import com.rique.walkseller.domain.Seller

@Composable
fun SellerMarkerDialog(seller: Seller, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        SellerMarkerDialogUI(seller)
    }
}

@Composable
fun SellerMarkerDialogUI(seller: Seller) {
    val context = LocalContext.current

    Card(
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(24.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Image(
                painter = painterResource(id = R.drawable.product),
                contentDescription = seller.title,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )
            Text(
                text = seller.title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )
            Divider(
                color = Color.Gray, modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = seller.description,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            FowardButton(
                onClick = { Utils.showToast(context, "Products Button was clicked") },
                title = stringResource(id = R.string.products),
                contentDescription = stringResource(id = R.string.see_products),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(alignment = Alignment.CenterHorizontally)
            )
        }
    }
}