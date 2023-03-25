package com.denisatrif.truthdare.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.denisatrif.truthdare.R
import com.denisatrif.truthdare.db.model.QuestionType
import com.denisatrif.truthdare.viewmodel.GameViewModel
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination
fun ComposeModesScreen(navController: NavHostController) {
    val viewModel = hiltViewModel<GameViewModel>()

    Background()
    Column(
        modifier = Modifier
            .fillMaxSize()

    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()

        ) {
            Image(
                painter = painterResource(R.drawable.bg_pink),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .clickable { }
            )
        }

        Image(
            painter = painterResource(R.drawable.bg_skin),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { }
        )
        Image(
            painter = painterResource(R.drawable.bg_green),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { }
        )
    }
}

fun startGame(questionType: QuestionType) {

}
