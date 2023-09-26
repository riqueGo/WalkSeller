package com.rique.walkseller.ui.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.rique.walkseller.di.LocalNavControllerProvider

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProductsScreenCover(coverImageUrl: String, modifier: Modifier = Modifier){
    val navController = LocalNavControllerProvider.current

    Box(
        modifier = modifier
    ) {
        GlideImage(
            model = coverImageUrl,
            contentDescription = "cover",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.align(Alignment.TopStart)
        ) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
        }
    }
}