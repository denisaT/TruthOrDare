package com.denisatrif.truthdare.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.denisatrif.truthdare.R
import com.denisatrif.truthdare.compose.destinations.ComposeTruthDareScreenDestination
import com.denisatrif.truthdare.db.model.QuestionType
import com.denisatrif.truthdare.ui.theme.fontFamilyMontserrat
import com.denisatrif.truthdare.viewmodel.GameViewModel
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination
fun ComposeModesScreen(navController: NavHostController) {
    val viewModel = hiltViewModel<GameViewModel>()

    MaterialTheme {

        Background {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.modes_version1),
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxWidth()
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center),
                        verticalArrangement = Arrangement.Center
                    ) {
                        ModeButton(
                            title = stringResource(id = R.string.sexy),
                            subtitle = stringResource(id = R.string.sexy_subtitle)
                        ) {
                            startSexyGame()
                            navController.navigate(ComposeTruthDareScreenDestination.route)
                        }
                        ModeButton(
                            title = stringResource(id = R.string.dirty),
                            subtitle = stringResource(id = R.string.dirty_subtitle)
                        ) {
                            startDirtyGame()
                            navController.navigate(ComposeTruthDareScreenDestination.route)
                        }
                        ModeButton(
                            title = stringResource(id = R.string.party),
                            subtitle = stringResource(id = R.string.party_subtitle)
                        ) {
                            startPartyGame()
                            navController.navigate(ComposeTruthDareScreenDestination.route)
                        }
                    }
                }
            }
        }
    }
}

private fun startSexyGame() {
    //get players
    //go to game screen - build it
}

private fun startPartyGame() {}

private fun startDirtyGame() {}

@Composable
fun ModeButton(title: String, subtitle: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .handleClick(onClick)
            .fillMaxWidth()
            .padding(
                start = 50.dp,
                top = 50.dp
            )
    ) {

        Text(
            text = title,
            fontSize = 26.sp,
            style = TextStyle(
                fontFamily = fontFamilyMontserrat,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )
        )


        Text(
            text = subtitle,
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

fun startGame(questionType: QuestionType) {

}
