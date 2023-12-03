package com.bestcoders.zaheed.presentation.screens.profile_screens.profile.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.ui.theme.CustomColor

@Composable
fun LanguageDialog(
    onSelect: (language: String) -> Unit,
) {
    val onDismiss = {}
    AlertDialog(
        properties = DialogProperties(
            dismissOnClickOutside = true,
            dismissOnBackPress = true,
        ),
        onDismissRequest = { onDismiss() },
        title = { Text(stringResource(R.string.choose_language)) },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = CustomColor.White),
                onClick = {
                    onSelect(Constants.ARABIC_LANGUAGE_CODE)
                    onDismiss()
                }
            ) {
                Text(
                    text = stringResource(R.string.arabic),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            }
        },
        dismissButton = {
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = CustomColor.White),
                onClick = {
                    onSelect(Constants.ENGLISH_LANGUAGE_CODE)
                    onDismiss()
                }
            ) {
                Text(
                    text = stringResource(R.string.english),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            }
        }
    )
}
