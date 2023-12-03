package com.bestcoders.zaheed.presentation.screens.track.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.extentions.convertTimestamp
import com.bestcoders.zaheed.ui.theme.AppTheme
import org.joda.time.LocalDateTime

@Composable
fun OrderDateTimeDetailsComponent(
    text: String,
    isActive: Boolean = true,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.small),
        content = {
            OrderStatusSubImageComponent(
                image = R.drawable.clock_icon,
                isActive  = isActive,
            )
            Text(
                text = LocalDateTime().convertTimestamp(text),
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.secondary.copy(
                        alpha = 0.5f
                    ),
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
    )
}

