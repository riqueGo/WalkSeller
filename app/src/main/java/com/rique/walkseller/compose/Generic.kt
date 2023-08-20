package com.rique.walkseller.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.rique.walkseller.R

@Composable
fun FowardButton(onClick: () -> Unit, title: String, contentDescription: String, modifier: Modifier = Modifier) {
    Button(
        onClick = {
            onClick()
        },
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = title)
            Icon(
                painter = painterResource(id = R.drawable.white_arrow_forward),
                contentDescription = contentDescription,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}