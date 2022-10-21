package com.denisatrif.truthdare.compose

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.denisatrif.truthdare.R
import com.denisatrif.truthdare.ui.theme.PrimaryColor
import com.denisatrif.truthdare.ui.theme.SecondaryColor
import com.denisatrif.truthdare.ui.theme.TruthOrDareTheme
import com.denisatrif.truthdare.viewmodel.ComposePlayersViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun ComposablePlayersScreen(viewModel: ComposePlayersViewModel = viewModel()) {
    val painterGirl = painterResource(id = R.drawable.girl_2)
    val descGirl = "CD Girl"
    val painterBoy = painterResource(id = R.drawable.boy_2)
    val descBoy = "CD Boy"

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded }
    )
    val coroutineScope = rememberCoroutineScope()

    BackHandler(sheetState.isVisible) {
        coroutineScope.launch { sheetState.hide() }
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            BottomSheet {
                coroutineScope.launch {
                    if (sheetState.isVisible) sheetState.hide()
                }
                viewModel.addPlayer(it)
            }
        },
        modifier = Modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Background()
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(vertical = 8.dp, horizontal = 6.dp),
                    shape = RoundedCornerShape(30.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .clickable {
                                coroutineScope.launch {
                                    if (sheetState.isVisible) sheetState.hide()
                                    else sheetState.show()
                                }
                            }
                            .fillMaxSize()
                            .background(SecondaryColor),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            modifier = Modifier.padding(start = 8.dp),
                            painter = painterGirl,
                            contentDescription = descGirl,
                            contentScale = ContentScale.Crop
                        )
                        Image(
                            modifier = Modifier.padding(start = 8.dp),
                            painter = painterBoy,
                            contentDescription = descBoy,
                            contentScale = ContentScale.Crop
                        )
                        Text(
                            text = stringResource(id = R.string.add_a_new_player),
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

                PlayersList(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f, true),
                    list = viewModel.playersList,
                    onRemovePlayer = {
                        viewModel.removePlayer(it)
                    }
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(110.dp),
                    shape = RoundedCornerShape(topStartPercent = 80)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(PrimaryColor)
                            .clickable {
                                //TODO navigate to the next screen
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.all_right_everything_set),
                            fontSize = 24.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.wrapContentSize()
                        )
                    }
                }
            }
        }
    }
}