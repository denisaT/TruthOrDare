package com.denisatrif.truthdare.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.denisatrif.truthdare.ui.theme.SecondaryColor
import com.denisatrif.truthdare.ui.theme.WhiteWithTransparency
import com.denisatrif.truthdare.ui.theme.fontFamilyMontserrat
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination
fun ComposeQuestionScreen(navController: NavHostController) {

    Background()
    Column(
        modifier = Modifier
            .padding(start = 24.dp, end = 24.dp, top = 128.dp, bottom = 128.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    WhiteWithTransparency,
                    shape = RoundedCornerShape(topEndPercent = 20, bottomStartPercent = 20)
                )

        ) {
            val firstShape = RoundedCornerShape(topEndPercent = 80, bottomStartPercent = 80)
            Text(
                text = "Matilda",
                style = TextStyle(
                    fontFamily = fontFamilyMontserrat,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                ),
                fontSize = 28.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(128.dp)
                    .background(SecondaryColor, firstShape)
            )
            val secondShape = RoundedCornerShape(bottomStartPercent = 20)
            Text(
                text = "BLa bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla",
                style = TextStyle(
                    fontFamily = fontFamilyMontserrat,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                ),
                fontSize = 24.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(24.dp)
            )
        }
    }
}
