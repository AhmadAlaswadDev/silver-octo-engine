package com.bestcoders.zaheed.presentation.screens.track.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight

@Composable
fun OrderStatusTextComponent(
    text: Int,
    isActive: Boolean = true,
) {
    Text(
        text = stringResource(text),
        style = MaterialTheme.typography.bodyLarge.copy(
            color = if (isActive) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)
            },
            fontWeight = FontWeight.SemiBold
        )
    )
}
