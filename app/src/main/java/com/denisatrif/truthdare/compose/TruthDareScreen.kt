package com.denisatrif.truthdare.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.denisatrif.truthdare.R
import com.denisatrif.truthdare.ui.theme.PurpleColorWithTransparency
import com.denisatrif.truthdare.ui.theme.SecondaryColorWithTransparency
import com.denisatrif.truthdare.ui.theme.fontFamilyMontserrat
import com.denisatrif.truthdare.viewmodel.GameViewModel
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination
fun TruthDareScreen(navController: NavHostController) {
    val viewModel = hiltViewModel<GameViewModel>()
    MaterialTheme {

        Background()
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { }
                    .background(SecondaryColorWithTransparency)
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "bla",
                    fontSize = 20.sp,
                    style = TextStyle(
                        fontFamily = fontFamilyMontserrat,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start
                    )
                )

                Text(
                    text = stringResource(id = R.string.truth),
                    fontSize = 40.sp,
                    style = TextStyle(
                        fontFamily = fontFamilyMontserrat,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start
                    )
                )
                Text(
                    text = stringResource(id = R.string.answer_one_question),
                    fontSize = 20.sp,
                    style = TextStyle(
                        fontFamily = fontFamilyMontserrat,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start
                    )
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { }
                    .background(PurpleColorWithTransparency)
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.dare),
                    fontSize = 40.sp,
                    style = TextStyle(
                        fontFamily = fontFamilyMontserrat,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start
                    )
                )
                Text(
                    text = stringResource(id = R.string.complete_a_practical_challenge),
                    fontSize = 20.sp,
                    style = TextStyle(
                        fontFamily = fontFamilyMontserrat,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start
                    )
                )
            }


        }
    }
}
