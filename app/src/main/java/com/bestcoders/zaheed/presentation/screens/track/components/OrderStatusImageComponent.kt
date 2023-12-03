package com.bestcoders.zaheed.presentation.screens.track.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.ui.theme.AppTheme

@Composable
fun OrderStatusImageComponent(
    modifier: Modifier = Modifier,
    image: Int,
    showImage: Boolean = true,
) {
    Image(
        modifier = modifier.size(AppTheme.dimens.orderStatusTrackOrderImageSize),
        painter = if (showImage) {
            painterResource(id = image)
        } else {
            painterResource(id = R.drawable.order_track_statuc_disabled_icon)
        },
        contentDescription = null
    )
}