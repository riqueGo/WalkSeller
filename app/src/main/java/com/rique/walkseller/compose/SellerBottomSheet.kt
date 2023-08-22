package com.rique.walkseller.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rique.walkseller.domain.Seller

@Composable
fun SellerBottomSheetContent(sellers: List<Seller>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Sellers",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Divider(color = androidx.compose.ui.graphics.Color.Gray, modifier = Modifier.padding(bottom = 16.dp))

        sellers.forEach { seller ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
//                Image(
//                    painter = painterResource(id = R.drawable.delivery_dining),
//                    contentDescription = seller.title,
//                    contentScale = ContentScale.FillWidth,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(150.dp)
//                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = seller.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = seller.description)
            }
        }
    }
}
