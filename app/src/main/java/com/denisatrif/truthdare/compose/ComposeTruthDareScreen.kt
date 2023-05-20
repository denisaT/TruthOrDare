package com.denisatrif.truthdare.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
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
import com.denisatrif.truthdare.db.model.Player
import com.denisatrif.truthdare.db.model.QuestionType
import com.denisatrif.truthdare.model.TruthDareEnum
import com.denisatrif.truthdare.ui.theme.PurpleColor
import com.denisatrif.truthdare.ui.theme.SecondaryColor
import com.denisatrif.truthdare.viewmodel.PlayersViewModel
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination
fun ComposeTruthDareScreen(
    navController: NavHostController,
    type: QuestionType,
    currentIndex: Int = 0
) {
    val playersViewModel = hiltViewModel<PlayersViewModel>()

    var myPlayer: Player? by remember { mutableStateOf(null) }

    playersViewModel.getNext(currentIndex).observeForever {
        myPlayer = it
    }
    print("${myPlayer} DENISA abc")
    Background(
        coverFullScreen = true,
        showAntet = false
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            Card(modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .alpha(0.5f)
                .clickable {
                    navController.navigate(
                        ComposeQuestionScreenDestination(
                            type = type,
                            truthOrDare = TruthDareEnum.TRUTH,
                            currentPlayerIndex = currentIndex,
                            playerName = myPlayer!!.name
                        ).route
                    )
                }) {
                Column(
                    modifier = Modifier
                        .background(SecondaryColor)
                        .padding(top = 100.dp, bottom = 100.dp)
                ) {
                    Text(
                        text = myPlayer?.name ?: "",
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
                        text = stringResource(id = R.string.answer_one_question),
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
                    navController.navigate(
                        ComposeQuestionScreenDestination(
                            type = type,
                            truthOrDare = TruthDareEnum.DARE,
                            currentPlayerIndex = currentIndex,
                            playerName = myPlayer!!.name
                        ).route
                    )
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
                        text = stringResource(id = R.string.complete_a_practical_challenge),
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



