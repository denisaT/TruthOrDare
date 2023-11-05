package com.denisatrif.truthdare.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.navigation.NavHostController
import com.denisatrif.truthdare.R
import com.denisatrif.truthdare.compose.destinations.ComposeTruthDareScreenDestination
import com.denisatrif.truthdare.db.model.QuestionType
import com.denisatrif.truthdare.ui.theme.fontFamilyMontserrat
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination
fun ComposeModesScreen(navController: NavHostController) {
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
                            navController.navigate(ComposeTruthDareScreenDestination(type = QuestionType.SEXY).route)
                        }
                        ModeButton(
                            title = stringResource(id = R.string.dirty),
                            subtitle = stringResource(id = R.string.dirty_subtitle)
                        ) {
                            navController.navigate(ComposeTruthDareScreenDestination(type = QuestionType.DIRTY).route)
                        }
                        ModeButton(
                            title = stringResource(id = R.string.party),
                            subtitle = stringResource(id = R.string.party_subtitle)
                        ) {
                            navController.navigate(ComposeTruthDareScreenDestination(type = QuestionType.PARTY).route)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ModeButton(title: String, subtitle: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .handleClick(onClick)
            .fillMaxWidth()
            .padding(
                bottom = 20.dp,
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
