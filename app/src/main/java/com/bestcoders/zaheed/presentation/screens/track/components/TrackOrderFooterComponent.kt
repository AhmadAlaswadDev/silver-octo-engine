package com.bestcoders.zaheed.presentation.screens.track.components

import android.os.CountDownTimer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.ui.theme.AppTheme
import java.text.SimpleDateFormat
import kotlin.math.max

@Composable
fun TrackOrderFooterComponent(startTime: String) {
    var currentTimeMillis by remember { mutableLongStateOf(System.currentTimeMillis()) }
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'")
    val startMillis = dateFormat.parse(startTime)?.time ?: 0
    val endTimeMillis = startMillis + (48 * 60 * 60 * 1000) // 48 hours in milliseconds

    DisposableEffect(Unit) {
        val timer = object : CountDownTimer(endTimeMillis - currentTimeMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                currentTimeMillis = System.currentTimeMillis()
            }

            override fun onFinish() {}
        }
        timer.start()
        onDispose {
            timer.cancel()
        }
    }

    val remainingTimeMillis = max(0, endTimeMillis - currentTimeMillis)
    val remainingTimeSeconds = (remainingTimeMillis / 1000).toInt()
    val remainingHours = remainingTimeSeconds / 3600

    val remainingTimeString = String.format("%02d", remainingHours,)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.dimens.paddingHorizontal),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.small),
        content = {
            Icon(
                modifier = Modifier.size(AppTheme.dimens.trackOrderSubImageSize),
                imageVector = Icons.Default.Info,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
            )

            Text(
                text =
                stringResource(id = R.string.the_order_must_be_received_within)
                        + remainingTimeString
                        + stringResource(id = R.string.hours_of_the_request),
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold,
                ),
            )
        }
    )
}