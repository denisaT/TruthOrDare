package com.denisatrif.truthdare.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.denisatrif.truthdare.R
import com.denisatrif.truthdare.compose.dialog.ComposeLanguageChangeDialog
import com.denisatrif.truthdare.compose.dialog.DescriptionDialog

@Composable
fun Background(
    showAntet: Boolean = true, coverFullScreen: Boolean = false, content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.lips_background),
            contentDescription = "Lips background",
            contentScale = ContentScale.Crop,
            alpha = 0.9f
        )
        if (showAntet) {
            Antet(content, coverFullScreen)
        } else {
            content()
        }
    }
}

@Composable
fun Antet(
    content: @Composable () -> Unit, coverFullScreen: Boolean
) {
    Column {
        FirstRow()
        if (!coverFullScreen) {
            content()
        }
    }
    if (coverFullScreen) {
        content()
    }
}

@Composable
fun FirstRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        var languageDialog by remember {
            mutableStateOf(false)
        }

        if (languageDialog) {
            ComposeLanguageChangeDialog(
                setShowDialog = {
                    languageDialog = it
                },
                onClose = { languageDialog = false }
            )
        }

        Image(
            modifier = Modifier
                .padding(24.dp)
                .clickable {
                    languageDialog = true
                },
            painter = painterResource(id = R.drawable.ic_language_24),
            contentDescription = "world button",
            alignment = Alignment.TopStart,
        )

        var description by remember {
            mutableStateOf(false)
        }

        if (description) {
            DescriptionDialog(
                setShowDialog = {
                    description = it
                },
                onClose = { description = false }
            )
        }
        Image(
            modifier = Modifier
                .padding(24.dp)
                .clickable {
                    description = true
                },
            alignment = Alignment.TopEnd,
            painter = painterResource(id = R.drawable.logo_sm),
            contentDescription = "logo image",
        )
    }
    Spacer(modifier = Modifier.height(24.dp))
}
