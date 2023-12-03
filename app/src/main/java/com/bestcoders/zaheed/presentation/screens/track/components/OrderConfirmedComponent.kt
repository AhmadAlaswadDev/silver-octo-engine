package com.bestcoders.zaheed.presentation.screens.track.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
fun OrderConfirmedComponent(track: Track) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.small),
        content = {
            OrderStatusImageComponent(
                image = R.drawable.success_track_order_icon,
                showImage = track.isOrderConfirmed,
            )
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
                content = {
                    OrderStatusTextComponent(
                        text = R.string.confirmed,
                        isActive = track.isOrderConfirmed
                    )
                    OrderDateTimeDetailsComponent(
                        text = track.orderConfirmedAt.toString(),
                        isActive = track.isOrderConfirmed
                    )
                }
            )
        },
    )
}