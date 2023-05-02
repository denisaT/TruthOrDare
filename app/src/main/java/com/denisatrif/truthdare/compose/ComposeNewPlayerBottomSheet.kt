package com.denisatrif.truthdare.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.denisatrif.truthdare.R
import com.denisatrif.truthdare.db.model.Player
import com.denisatrif.truthdare.ui.theme.GrayPaleWithTransparency
import com.denisatrif.truthdare.ui.theme.SecondaryColor
import com.denisatrif.truthdare.ui.theme.WhiteWithTransparency

@Preview
@Composable
fun BottomSheet(onDismiss: (newPlayer: Player) -> Unit = {}) {
    var textFieldState by remember {
        mutableStateOf("")
    }
    var genderState by remember {
        mutableStateOf(true)
    }

    val painterGirlUnselected = painterResource(id = R.drawable.girl_unselected_faded)
    val descGirlUnselected = "CD Girl Unelected"
    val painterGirlSelected = painterResource(id = R.drawable.girl_selected)
    val descGirlSelected = "CD Girl Selected"

    val painterBoyUnselected = painterResource(id = R.drawable.boy_unselected_faded)
    val descBoyUnselected = "CD BoySelected"
    val painterBoySelected = painterResource(id = R.drawable.boy_selected)
    val descBoySelected = "CD BoySelected"

    Column(
        modifier = Modifier
            .wrapContentHeight()
            .background(WhiteWithTransparency)
    ) {
        Spacer(modifier = Modifier.height(36.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.gender),
                fontSize = 20.sp,
                color = Color.White,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(horizontal = 8.dp)
            )
            Image(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(8.dp)
                    .clickable {
                        if (!genderState) {
                            genderState = true
                        }
                    },
                painter = if (genderState) {
                    painterGirlSelected
                } else {
                    painterGirlUnselected
                },
                contentDescription = if (genderState) {
                    descGirlSelected
                } else {
                    descGirlUnselected
                },
                contentScale = ContentScale.Crop
            )
            Image(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(vertical = 8.dp)
                    .clickable {
                        if (genderState) {
                            genderState = false
                        }
                    },
                painter = if (genderState) {
                    painterBoyUnselected
                } else {
                    painterBoySelected
                },
                contentDescription = if (genderState) {
                    descBoyUnselected
                } else {
                    descBoySelected
                },
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(30.dp),
            backgroundColor = WhiteWithTransparency
        ) {
            val focus = LocalTextInputService.current
            TextField(
                value = textFieldState,
                label = {
                    Text(
                        text = stringResource(id = R.string.enter_player_name),
                        style = MaterialTheme.typography.body2,
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    backgroundColor = GrayPaleWithTransparency,
                    cursorColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White,
                ),
                onValueChange = {
                    textFieldState = it
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Text
                ),
                keyboardActions = KeyboardActions(onDone = { focus?.hideSoftwareKeyboard() }),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(fontSize = 20.sp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(30.dp)
        ) {
            Button(
                onClick = {
                    if (textFieldState.isNotEmpty()) {
                        onDismiss(
                            Player.getEmpty(
                                gender = genderState,
                                name = textFieldState
                            )
                        )
                        textFieldState = ""
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = SecondaryColor,
                    contentColor = Color.White
                ),
            ) {
                Text(
                    text = stringResource(id = R.string.add_this_player),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(36.dp))
    }
}
