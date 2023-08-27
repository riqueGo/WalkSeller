package com.rique.walkseller.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rique.walkseller.R
import com.rique.walkseller.domain.Product
import com.rique.walkseller.domain.Seller
import com.rique.walkseller.ui.viewModel.ProductsViewModel

@Composable
fun ProductsScreen(
    viewModel: ProductsViewModel,
    seller: Seller,
    navigateBack: () -> Unit
) {
    val productsState = viewModel.productsState.value

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Cover section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.product),
                contentDescription = "Cover Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            IconButton(
                onClick = navigateBack,
                modifier = Modifier.align(Alignment.TopStart)
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }
            IconButton(
                onClick = {},
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(imageVector = Icons.Default.Favorite, contentDescription = "Favorite")
            }
        }

        // Seller description section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(text = seller.title, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text(text = seller.description, modifier = Modifier.padding(vertical = 8.dp))
            }
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.product),
                    contentDescription = "Cover Image",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        // Products section
        Divider(modifier = Modifier.padding(vertical = 8.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(productsState.products) { product ->
                ProductCard(product = product)
            }
        }

        // Cart section
        // Replace with your Cart modal
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//                .align(Alignment.BottomCenter)
//        ) {
//            Button(onClick = { /* Open Cart modal */ }) {
//                Text(text = "View Cart")
//            }
//        }
        LaunchedEffect(seller.id) {
            viewModel.loadProducts(seller.id)
        }
    }
}

@Composable
fun ProductCard(product: Product) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
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
                Image(
                    painter = painterResource(id = R.drawable.product),
                    contentDescription = "Product Image",
                    contentScale = ContentScale.FillBounds
                )
            }

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = product.name, fontWeight = FontWeight.Bold)
                Text(text = product.description, modifier = Modifier.padding(vertical = 4.dp), fontWeight = FontWeight.Light)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "$${product.price}", color = MaterialTheme.colorScheme.primary, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    QuantityControl()
                }
            }
        }
        Divider(modifier = Modifier.padding(vertical = 8.dp))
    }
}

@Composable
fun QuantityControl() {
    Card(
        modifier = Modifier.padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            IconButton(
                onClick = { /* TODO: Decrement quantity */ }
            ) {
                Icon(imageVector = Icons.Default.Clear, contentDescription = "Decrement")
            }
            Text(text = "1") // TODO: Display actual quantity
            IconButton(
                onClick = { /* TODO: Increment quantity */ }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Increment")
            }
        }
    }
}
