package com.denisatrif.truthdare.compose

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.denisatrif.truthdare.R
import com.denisatrif.truthdare.billing.BillingClientWrapper
import com.denisatrif.truthdare.billing.PARTY_PACK_ID
import com.denisatrif.truthdare.billing.SEXY_PACK_ID
import com.denisatrif.truthdare.compose.destinations.ComposePlayersScreenDestination
import com.denisatrif.truthdare.compose.destinations.ComposeTruthDareScreenDestination
import com.denisatrif.truthdare.db.model.QuestionType
import com.denisatrif.truthdare.ui.theme.PrimaryColorWithTransparency
import com.denisatrif.truthdare.ui.theme.SecondaryColorWithTransparency
import com.denisatrif.truthdare.ui.theme.fontFamilyMontserrat
import com.denisatrif.truthdare.utils.findAndroidActivity
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination
fun ComposeModesScreen(navController: DestinationsNavigator) {
    BackHandler(enabled = true) {
        navController.navigate(ComposePlayersScreenDestination.route)
        //TODO add dialog
    }
    MaterialTheme {
        val context = LocalContext.current
        val activity = context.findAndroidActivity()
        val billingStuff = BillingClientWrapper(context, activity!!)
        Background {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center),
                        verticalArrangement = Arrangement.Center
                    ) {
                        ModeButton(
                            title = stringResource(id = R.string.party),
                            subtitle = stringResource(id = R.string.party_subtitle),
                            color = PrimaryColorWithTransparency
                        ) {
                            if (billingStuff.shouldStartBillingFlow(PARTY_PACK_ID)) {
                                billingStuff.launchBillingForId(PARTY_PACK_ID)
                            } else {
                                navController.navigate(ComposeTruthDareScreenDestination(type = QuestionType.PARTY).route)
                            }


                        }
                        ModeButton(
                            title = stringResource(id = R.string.sexy),
                            subtitle = stringResource(id = R.string.sexy_subtitle),
                            color = SecondaryColorWithTransparency,
                        ) {
                            if (billingStuff.shouldStartBillingFlow(SEXY_PACK_ID)) {
                                billingStuff.launchBillingForId(SEXY_PACK_ID)
                            } else {
                                navController.navigate(
                                    ComposeTruthDareScreenDestination(
                                        type = QuestionType.SEXY
                                    ).route
                                )
                            }
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun ModeButton(
    title: String,
    subtitle: String,
    color: Color,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(24.dp)
            .handleClick(onClick)
            .fillMaxWidth()
            .background(
                color,
                shape = RoundedCornerShape(
                    topEndPercent = 20,
                    bottomStartPercent = 20
                )
            ), verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = title,
            modifier = Modifier.padding(all = 20.dp),
            fontSize = 26.sp, style = TextStyle(
                fontFamily = fontFamilyMontserrat,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
            )
        )

        Text(
            text = subtitle,
            modifier = Modifier.padding(all = 20.dp),
            fontSize = 20.sp, style = TextStyle(
                fontFamily = fontFamilyMontserrat,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )
        )
    }
}



