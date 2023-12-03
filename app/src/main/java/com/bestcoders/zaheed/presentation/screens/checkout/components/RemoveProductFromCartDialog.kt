package com.bestcoders.zaheed.presentation.screens.checkout.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.ui.theme.CustomColor

@Composable
fun RemoveProductFromCartDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.remove_product)) },
        text = { Text(stringResource(R.string.are_you_sure_you_want_to_remove_this_product_from_the_cart)) },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onPrimary),
                onClick = {
                    onConfirm()
                    onDismiss()
                }
            ) {
                Text(
                    stringResource(R.string.yes),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = CustomColor.White
                    )
                )
            }
        },
        dismissButton = {
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = CustomColor.White),
                onClick = onDismiss
            ) {
                Text(
                    stringResource(R.string.no),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            }
        }
    )
}
