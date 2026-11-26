package com.denisatrif.truthdare.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.denisatrif.truthdare.R
import com.denisatrif.truthdare.db.model.Player
import com.denisatrif.truthdare.ui.theme.WhiteWithTransparency

@Composable
fun PlayersList(
    list: List<Player>,
    onRemovePlayer: (Player) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(items = list, key = { player -> player.id }) { player ->
            PlayersListItem(modifier, player, onRemovePlayer)
        }
    }
}

@Preview
@Composable
fun PlayersListItem(
    modifier: Modifier = Modifier,
    player: Player = Player.getEmpty(),
    onRemove: (Player) -> Unit = {},
) {
    val painterGirl = painterResource(id = R.drawable.girl_unselected)
    val descGirl = "CD Girl"
    val painterBoy = painterResource(id = R.drawable.boy_unselected)
    val descBoy = "CD Boy"
    val painterMinus = painterResource(id = R.drawable.ic_minus_svg)
    val descMinus = "CD Minus"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .padding(vertical = 8.dp, horizontal = 6.dp),
        shape = RoundedCornerShape(30.dp),
        backgroundColor = WhiteWithTransparency
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val painter = if (player.gender) painterGirl else painterBoy
            val desc = if (player.gender) descGirl else descBoy
            Image(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(8.dp),
                painter = painter,
                contentDescription = desc,
                contentScale = ContentScale.Crop
            )
            Text(
                text = player.name,
                fontSize = 20.sp,
                color = Color.White,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .wrapContentHeight()
                    .weight(1f, true)
            )
            Image(
                modifier = Modifier
                    .clickable { onRemove.invoke(player) }
                    .padding(8.dp),
                painter = painterMinus,
                contentDescription = descMinus,
                contentScale = ContentScale.Fit,
            )
        }
    }
}
