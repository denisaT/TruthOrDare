package com.denisatrif.truthdare.compose.dialog

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.content.pm.PackageInfoCompat
import com.denisatrif.truthdare.R


@Composable
fun DescriptionDialog(
    context: Context,
    setShowDialog: (Boolean) -> Unit,
    onClose: () -> Unit,
) {
    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            shape = RoundedCornerShape(16.dp), color = Color.White
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(stringResource(id = R.string.game_description_short))
                var versionNumber = 0L
                try {
                    val packageManager = context.packageManager
                    val packageName = context.packageName
                    val packageInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        packageManager.getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(0))
                    } else {
                        packageManager.getPackageInfo(packageName, 0)
                    }
                    versionNumber = PackageInfoCompat.getLongVersionCode(packageInfo)
                } catch (e: Exception) {
                    null
                }
                Text(stringResource(id = R.string.version_number, versionNumber))
                Divider()

                TextButton(
                    onClick = {
                        onClose()
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = stringResource(id = R.string.ok),
                        color = Color.Black
                    )
                }
            }
        }
    }
}
