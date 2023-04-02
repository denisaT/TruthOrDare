package com.denisatrif.truthdare.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.denisatrif.truthdare.R
import com.denisatrif.truthdare.compose.destinations.ComposeQuestionScreenDestination
import com.denisatrif.truthdare.db.model.QuestionType
import com.denisatrif.truthdare.ui.theme.PurpleColor
import com.denisatrif.truthdare.ui.theme.SecondaryColor
import com.denisatrif.truthdare.viewmodel.PlayersViewModel
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination
fun ComposeTruthDareScreen(navController: NavHostController) {
    val viewModel = hiltViewModel<PlayersViewModel>()

    Background(coverFullScreen = true) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Card(modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .alpha(0.5f)
                .clickable {
                    showQuestion(QuestionType.DIRTY, navController)
                }) {
                Column(
                    modifier = Modifier
                        .background(SecondaryColor)
                        .padding(top = 100.dp, bottom = 100.dp)
                ) {
                    Text(
                        text = "Matilda,",
                        fontSize = 30.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Text(
                        text = stringResource(id = R.string.truth),
                        fontSize = 60.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Text(
                        text = "Answer one question",
                        fontSize = 16.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }


            Card(modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .alpha(0.5f)
                .clickable {
                    showQuestion(QuestionType.DIRTY, navController)
                }) {
                Column(
                    modifier = Modifier
                        .background(PurpleColor)
                        .padding(top = 100.dp, bottom = 100.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.dare),
                        fontSize = 60.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Text(
                        text = "Complete a practical challenge",
                        fontSize = 16.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }
        }
    }

}

fun showQuestion(dirty: QuestionType, navController: NavHostController) {
    navController.navigate(ComposeQuestionScreenDestination.route)
}
