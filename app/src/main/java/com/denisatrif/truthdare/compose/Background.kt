package com.denisatrif.truthdare.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.denisatrif.truthdare.R

@Composable
fun Background(
    showAntet: Boolean = true, coverFullScreen: Boolean = false, content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.lips_background),
            contentDescription = "Lips background",
            contentScale = ContentScale.Crop,
            alpha = 0.9f
        )
        if (showAntet) {
            Antet(content, coverFullScreen)
        } else {
            content()
        }
    }
}

@Composable
fun Antet(
    content: @Composable () -> Unit, coverFullScreen: Boolean
) {
    Column {
        FirstRow()
        if (!coverFullScreen) {
            content()
        }
    }
    if (coverFullScreen) {
        content()
    }
}

@Composable
fun FirstRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.padding(24.dp),
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
}
