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
import com.denisatrif.truthdare.ui.theme.GrayPaleWithTransparency
import com.denisatrif.truthdare.ui.theme.PrimaryColor
import com.denisatrif.truthdare.ui.theme.SecondaryColor
import com.denisatrif.truthdare.viewmodel.GameViewModel
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination
fun ComposeModesScreen() {
    val viewModel = hiltViewModel<GameViewModel>()

    Background()
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            shape = AbsoluteRoundedCornerShape(bottomLeftPercent = 80)
        ) {
            Box(modifier = Modifier
                .background(SecondaryColor.copy(alpha=0.2f))
                .clickable {
                    startGame(QuestionType.DIRTY)
                }) {
                Text(
                    text = stringResource(id = R.string.dirty),
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp)
                )

            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            shape = AbsoluteRoundedCornerShape(bottomLeftPercent = 80)
        ) {
            Box(modifier = Modifier
                .background(PrimaryColor.copy(alpha=0.2f))
                .clickable {
                    startGame(QuestionType.SEXY)
                }) {
                Text(
                    text = stringResource(id = R.string.sexy),
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp)
                )

            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            shape = AbsoluteRoundedCornerShape(bottomLeftPercent = 80)
        ) {
            Box(modifier = Modifier
                .background(GrayPaleWithTransparency)
                .alpha(0.2f)
                .clickable {
                    startGame(QuestionType.PARTY)
                }) {
                Text(
                    text = stringResource(id = R.string.party),
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp)
                )

            }
        }
    }
}

fun startGame(questionType: QuestionType) {

}
