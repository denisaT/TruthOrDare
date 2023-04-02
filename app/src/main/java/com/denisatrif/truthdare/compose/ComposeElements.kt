package com.denisatrif.truthdare.compose

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.denisatrif.truthdare.ui.theme.PrimaryColor

fun getThreeFifths(configuration: Configuration): Dp {
    return configuration.screenHeightDp.dp * 3 / 5
}

@Composable
fun bottomYellowRoundedButton(text: String, behave: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                enabled = true, onClick = behave
            )
            .height(110.dp),
        shape = RoundedCornerShape(topStartPercent = 80),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(PrimaryColor),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                fontSize = 24.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.wrapContentSize()
            )
        }

    }
}
