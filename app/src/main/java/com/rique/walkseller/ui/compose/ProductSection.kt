package com.rique.walkseller.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.rique.walkseller.R
import com.rique.walkseller.di.LocalOrderViewModelProvider
import com.rique.walkseller.di.LocalSheetStateProvider
import com.rique.walkseller.domain.Product
import kotlinx.coroutines.launch

@Composable
fun ProductsList(products: List<Product>, modifier: Modifier = Modifier) {
    products.forEach { product ->
        ProductSection(
            product = product,
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProductSection(product: Product, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(RoundedCornerShape(4.dp))
            ) {
                GlideImage(
                    model = product.urlImage,
                    contentDescription = product.name,
                    contentScale = ContentScale.FillBounds
                )
            }

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = product.name, fontWeight = FontWeight.Bold)
                Text(
                    text = product.description,
                    modifier = Modifier.padding(vertical = 4.dp),
                    fontWeight = FontWeight.Light
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "R$${product.price}",
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    QuantityControl(product = product)
                }
            }
        }
        Divider(modifier = Modifier.padding(vertical = 8.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuantityControl(product: Product) {
    val orderViewModel = LocalOrderViewModelProvider.current
    val sheetState = LocalSheetStateProvider.current
    val orderState = orderViewModel.orderState.value
    val scope = rememberCoroutineScope()

    Card(
        modifier = Modifier.padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            IconButton(
                onClick = {
                    orderViewModel.subtractProduct(product)
                    if (orderViewModel.orderState.value.totalProductsQuantity == 0 && sheetState.isVisible) {
                        scope.launch {
                            sheetState.hide()
                        }
                    }
                }
            ) {
                Icon(painter = painterResource(id = R.drawable.minus_24), contentDescription = "Decrement", tint = Color.DarkGray)
            }
            Text(text = (orderState.productById[product.id]?.quantity ?: 0).toString())
            IconButton(
                onClick = {
                    orderViewModel.addProduct(product)
                    if (!sheetState.isVisible) {
                        scope.launch {
                            sheetState.expand()
                        }
                    }
                }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Increment", tint = Color.DarkGray)
            }
        }
    }
}