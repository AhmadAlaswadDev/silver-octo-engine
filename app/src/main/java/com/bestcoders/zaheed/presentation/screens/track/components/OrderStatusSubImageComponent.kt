package com.bestcoders.zaheed.presentation.screens.track.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.bestcoders.zaheed.ui.theme.AppTheme

@Composable
fun OrderStatusSubImageComponent(
    image: Int,
    isActive: Boolean = true,
) {
    Icon(
        modifier = Modifier.size(AppTheme.dimens.trackOrderSubImageSize),
        painter = painterResource(id = image),
        contentDescription = null,
        tint = if (isActive) {
            MaterialTheme.colorScheme.onTertiary
        } else {
            MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f)
        }
    )
}