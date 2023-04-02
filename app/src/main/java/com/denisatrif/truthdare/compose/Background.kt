package com.denisatrif.truthdare.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.denisatrif.truthdare.R

@Composable
fun Background(
    showAntet: Boolean = true,
    coverFullScreen: Boolean = false,
    content: @Composable () -> Unit
) {
    val painterBkg = painterResource(id = R.drawable.lips_background)
    val descriptionBkg = "Lips bkg"

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Image(
            painter = painterBkg,
            contentDescription = descriptionBkg,
            contentScale = ContentScale.Crop,
            alpha = 0.8f
        )
        if (showAntet) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier
                            .padding(24.dp),
                        painter = painterResource(id = R.drawable.hamburger),
                        contentDescription = "hamburger button",
                        alignment = Alignment.TopStart
                    )
                    Image(
                        modifier = Modifier.padding(24.dp),
                        alignment = Alignment.TopEnd,
                        painter = painterResource(id = R.drawable.logo_sm),
                        contentDescription = "hamburger button",
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                content()
            }
        }
    }

}
