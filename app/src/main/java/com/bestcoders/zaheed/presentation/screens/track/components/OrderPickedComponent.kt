package com.bestcoders.zaheed.presentation.screens.track.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.domain.model.track.Track
import com.bestcoders.zaheed.ui.theme.AppTheme

@Composable
fun OrderPickedComponent(track: Track) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.small),
        content = {
            OrderStatusImageComponent(
                image = R.drawable.success_track_order_icon,
                showImage = track.isOrderCompleted,
            )
            OrderStatusTextComponent(
                text = R.string.picked,
                isActive = track.isOrderCompleted
            )
        },
    )
}