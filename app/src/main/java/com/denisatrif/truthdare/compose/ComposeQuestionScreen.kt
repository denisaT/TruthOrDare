package com.denisatrif.truthdare.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.denisatrif.truthdare.R
import com.denisatrif.truthdare.compose.destinations.ComposeTruthDareScreenDestination
import com.denisatrif.truthdare.ui.theme.SecondaryColor
import com.denisatrif.truthdare.ui.theme.WhiteWithTransparency
import com.denisatrif.truthdare.ui.theme.fontFamilyMontserrat
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination
fun ComposeQuestionScreen(navController: NavHostController) {

    Background {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 24.dp)
                    .background(
                        WhiteWithTransparency,
                        shape = RoundedCornerShape(topEndPercent = 20, bottomStartPercent = 20)
                    ),
                verticalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(getThreeFifths(LocalConfiguration.current))
                ) {
                    val firstShape = RoundedCornerShape(bottomStartPercent = 80)
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
                            .padding(24.dp)
                            .background(SecondaryColor, firstShape)
                    )
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
                            .padding(24.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(24.dp))

            Column(Modifier.align(Alignment.BottomCenter)) {
                bottomYellowRoundedButton(text = stringResource(id = R.string.next_player)) {
                    navController.navigate(ComposeTruthDareScreenDestination.route)
                }
            }
        }
    }

}
