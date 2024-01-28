package com.denisatrif.truthdare.compose

import android.net.Uri
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.denisatrif.truthdare.R
import com.denisatrif.truthdare.compose.destinations.ComposeTruthDareScreenDestination
import com.denisatrif.truthdare.db.model.QuestionType
import com.denisatrif.truthdare.ui.theme.SecondaryColor
import com.denisatrif.truthdare.ui.theme.WhiteWithTransparency
import com.denisatrif.truthdare.ui.theme.fontFamilyMontserrat
import com.denisatrif.truthdare.utils.FakeDestinationsNavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Preview
@Composable
fun ComposeQuestionScreenPreview() {
    ComposeQuestionScreen(
        navController = FakeDestinationsNavController(),
        playerName = "Arthur",
        type = QuestionType.PARTY,
        playerId = 50,
        question = "Text your last ex (or a close friend) with the message, 'I just discovered I have a secret talent for interpretive dance. Want a private performance?'"
    )
}

@Composable
@Destination
fun ComposeQuestionScreen(
    navController: DestinationsNavigator,
    playerName: String,
    type: QuestionType,
    playerId: Int,
    question: String = ""
) {
    val finalPlayerName = Uri.decode(playerName)
    val finalQuestion = Uri.decode(question)

    handleBackDialog(navController = navController)

    Background {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .height(getThreeFifths(LocalConfiguration.current))
                    .background(
                        WhiteWithTransparency,
                        shape = RoundedCornerShape(
                            topEndPercent = 20,
                            bottomStartPercent = 20
                        )
                    ), verticalArrangement = Arrangement.Top
            ) {
                Box(
                    modifier = Modifier
                        .height(110.dp)
                        .fillMaxSize()
                        .background(SecondaryColor, RoundedCornerShape(bottomStartPercent = 80)),
                    contentAlignment = Alignment.Center

                ) {
                    Text(
                        text = finalPlayerName,
                        style = TextStyle(
                            fontFamily = fontFamilyMontserrat,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        ),
                        fontSize = 28.sp,
                        )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = finalQuestion, style = TextStyle(
                        fontFamily = fontFamilyMontserrat,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    ),
                    fontSize = 24.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .weight(weight = 1f, fill = false)
                        .padding(24.dp)

                )
                Spacer(modifier = Modifier.height(24.dp))
            }

            Column(Modifier.align(Alignment.BottomCenter)) {
                bottomYellowRoundedButton(text = stringResource(id = R.string.next_player)) {
                    navController.navigate(
                        ComposeTruthDareScreenDestination(
                            type,
                            id = playerId + 1
                        ).route,

                        )
                }
            }
        }
    }

}
