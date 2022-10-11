package com.denisatrif.truthdare.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.denisatrif.truthdare.R
import kotlinx.coroutines.delay

@Composable
fun ComposeEntryScreen(navController: NavHostController) {
    val painterLogo = painterResource(id = R.drawable.logo_big)
    val descriptionLogo = "Logo"

    Box(modifier = Modifier.fillMaxSize()) {
        Background()
        Image(
            painter = painterLogo,
            contentDescription = descriptionLogo,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 50.dp)
                .padding(bottom = 120.dp)
        )
        CircularProgressIndicator(
            modifier = Modifier
                .size(width = 70.dp, height = 110.dp)
                .align(Alignment.BottomCenter)
                .padding(bottom = 30.dp),
            color = Color.White,
            strokeWidth = 4.dp
        )
    }

    LaunchedEffect(key1 = true) {
        delay(2000L)
        navController.navigateAndClean(Screen.PlayersScreen.route)
    }
}
