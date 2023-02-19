package com.denisatrif.truthdare.compose

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.denisatrif.truthdare.R

@Composable
fun Background() {
    val painterBkg = painterResource(id = R.drawable.lips_background)
    val descriptionBkg = "Lips bkg"

    Image(
        painter = painterBkg,
        contentDescription = descriptionBkg,
        contentScale = ContentScale.Crop,
        alpha = 0.8f
    )
}
