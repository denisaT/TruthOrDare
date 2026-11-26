package com.denisatrif.truthdare.compose

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.denisatrif.truthdare.compose.dialog.BackButtonDialog
import com.denisatrif.truthdare.ui.theme.PrimaryColor
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

fun getThreeFifths(configuration: Configuration): Dp {
    return configuration.screenHeightDp.dp * 3 / 5
}

enum class ViewState { Pressed, Idle }


@Composable
fun handleBackDialog(navController: DestinationsNavigator) {
    var backDialog by remember {
        mutableStateOf(false)
    }

    if (backDialog) {
        BackButtonDialog(
            navController,
            setShowDialog = {
                backDialog = it
            },
            onClose = { backDialog = false }
        )
    }

    BackHandler(enabled = true) {
        backDialog = true
    }
}


fun Modifier.handleClick(onClick: () -> Unit) = composed {
    var buttonState by remember { mutableStateOf(ViewState.Idle) }
    val ty by animateFloatAsState(
        if (buttonState == ViewState.Pressed) 0.4f else 1f,
        animationSpec = tween(durationMillis = 200), label = ""
    )

    this
        .graphicsLayer {
            alpha = ty
        }
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = onClick
        )
        .pointerInput(buttonState) {
            awaitPointerEventScope {
                buttonState = if (buttonState == ViewState.Pressed) {
                    waitForUpOrCancellation()
                    ViewState.Idle
                } else {
                    awaitFirstDown(false)
                    ViewState.Pressed
                }
            }
        }
}

@Composable
fun bottomYellowRoundedButton(text: String, behave: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .handleClick(behave)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                enabled = true,
                onClick = behave
            )
            .height(110.dp),
        shape = RoundedCornerShape(topStartPercent = 80),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(PrimaryColor),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                fontSize = 24.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.wrapContentSize()
            )
        }

    }
}
