package com.denisatrif.truthdare.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
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
import com.denisatrif.truthdare.R
import com.denisatrif.truthdare.db.model.QuestionType
import com.denisatrif.truthdare.ui.theme.PrimaryColor
import com.denisatrif.truthdare.ui.theme.SecondaryColor
import com.denisatrif.truthdare.ui.theme.SkinColor
import com.denisatrif.truthdare.viewmodel.GameViewModel
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination
fun ComposeModesScreen() {
    val viewModel = hiltViewModel<GameViewModel>()

    Background()
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(0.5f)
                .height(200.dp),
            shape = AbsoluteRoundedCornerShape(bottomLeftPercent = 80)
        ) {
            Box(modifier = Modifier
                .background(SecondaryColor)
                .clickable {
                    startGame(QuestionType.DIRTY)
                }) {
                Text(
                    text = stringResource(id = R.string.dirty),
                    fontSize = 40.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .alpha(1f)
                        .padding(20.dp)
                )
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(0.5f)
                .height(200.dp),
            shape = AbsoluteRoundedCornerShape(bottomLeftPercent = 80)
        ) {
            Box(
                modifier = Modifier
                    .background(PrimaryColor)
                    .clickable {
                        startGame(QuestionType.SEXY)
                    }) {
                Text(
                    text = stringResource(id = R.string.sexy),
                    fontSize = 40.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .alpha(1f)
                )

            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(0.5f)
                .height(200.dp),
            shape = AbsoluteRoundedCornerShape(bottomLeftPercent = 80)
        ) {
            Box(modifier = Modifier
                .background(SkinColor)
                .clickable {
                    startGame(QuestionType.PARTY)
                }) {
                Text(
                    text = stringResource(id = R.string.party),
                    fontSize = 40.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .alpha(1f)
                )

            }
        }
    }
}

fun startGame(questionType: QuestionType) {

}
