package com.denisatrif.truthdare.compose

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.os.LocaleListCompat
import com.denisatrif.truthdare.R

@Composable
fun ComposeLanguageChangeDialog(setShowDialog: (Boolean) -> Unit, onClose: () -> Unit) {
    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            shape = RoundedCornerShape(16.dp), color = Color.White
        ) {
            val radioOptions =
                stringArrayResource(id = R.array.languages).asList()
            val languageCodes =
                stringArrayResource(id = R.array.languages_codes).asList()
            val currentLanguage = stringResource(id = R.string.lang)
            val index = stringArrayResource(id = R.array.languages_codes)
                .indexOf(currentLanguage)

            val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[index]) }
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(stringResource(id = R.string.choose_language))
                Divider()
                radioOptions.forEach { text ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = (text.contentEquals(selectedOption)),
                                onClick = {
                                    onOptionSelected(text)
                                }), verticalAlignment = Alignment.CenterVertically
                    ) {

                        RadioButton(
                            selected = (text.contentEquals(selectedOption)),
                            onClick = { onOptionSelected(text) })
                        Text(
                            text = text,
                            style = MaterialTheme.typography.body1.merge(),
                        )
                    }
                }
                Divider()
                TextButton(
                    onClick = {
                        val indexOfLanguage = radioOptions.indexOf(selectedOption)
                        AppCompatDelegate.setApplicationLocales(
                            LocaleListCompat.forLanguageTags(languageCodes[indexOfLanguage])
                        )
                        onClose()
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = "OK",
                        color = Color.Black
                    )
                }
            }
        }
    }
}

