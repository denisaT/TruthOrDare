package com.denisatrif.truthdare.compose

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.denisatrif.truthdare.R
import com.denisatrif.truthdare.compose.destinations.ComposePlayersScreenDestination
import com.denisatrif.truthdare.compose.destinations.ComposeTruthDareScreenDestination
import com.denisatrif.truthdare.db.model.QuestionType
import com.denisatrif.truthdare.model.TruthDareEnum
import com.denisatrif.truthdare.ui.theme.SecondaryColor
import com.denisatrif.truthdare.ui.theme.WhiteWithTransparency
import com.denisatrif.truthdare.ui.theme.fontFamilyMontserrat
import com.denisatrif.truthdare.viewmodel.GameViewModel
import com.ramcosta.composedestinations.annotation.Destination


@Composable
@Destination
fun ComposeQuestionScreen(
    navController: NavHostController,
    playerName: String,
    currentPlayerIndex: Int,
    type: QuestionType,
    truthOrDare: TruthDareEnum

) {
    val gameViewModel = hiltViewModel<GameViewModel>()

    var displayed by remember {
        mutableStateOf("")
    }

    if (truthOrDare == TruthDareEnum.TRUTH) {
        gameViewModel.getNextTruth(type).observeForever {
            displayed = it.question.toString()
        }
    } else {
        gameViewModel.getNextDare(type).observeForever {
            displayed = it.question.toString()
        }
    }

    BackHandler(enabled = true) {
        navController.navigate(ComposePlayersScreenDestination.route)
        //TODO add dialog
    }
    Background {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 24.dp)
                    .height(getThreeFifths(LocalConfiguration.current))
                    .background(
                        WhiteWithTransparency,
                        shape = RoundedCornerShape(topEndPercent = 20, bottomStartPercent = 20)
                    ), verticalArrangement = Arrangement.Top
            ) {
                Box(
                    modifier = Modifier
                        .height(110.dp)
                        .fillMaxSize()
                        .background(SecondaryColor, RoundedCornerShape(bottomStartPercent = 80)),
                    contentAlignment = Alignment.Center

                ) {
                    playerName?.let {
                        Text(
                            text = it,
                            style = TextStyle(
                                fontFamily = fontFamilyMontserrat,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            ),
                            fontSize = 28.sp,

                            )
                    }
                }


                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = displayed, style = TextStyle(
                        fontFamily = fontFamilyMontserrat,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    ), fontSize = 24.sp, modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                )
            }

            Column(Modifier.align(Alignment.BottomCenter)) {
                bottomYellowRoundedButton(text = stringResource(id = R.string.next_player)) {
                    navController.navigate(
                        ComposeTruthDareScreenDestination(
                            type,
                            currentIndex = currentPlayerIndex + 1
                        ).route,

                        )
                }
            }
        }
    }

}
