package com.bestcoders.zaheed.presentation.screens.product_details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.domain.model.products.ProductDetails
import com.bestcoders.zaheed.presentation.components.SpacerWidthMedium
import com.bestcoders.zaheed.ui.theme.AppTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Timer
import java.util.TimerTask
import java.util.concurrent.TimeUnit

@Composable
fun OfferTime(
    productDetails: ProductDetails
) {
    val calculatesRemainingTime = calculateRemainingTime(productDetails.offerEndDate)
    var remainingTime by remember { mutableStateOf(calculatesRemainingTime) }

    LaunchedEffect(productDetails.offerEndDate) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        val endDate = dateFormat.parse(productDetails.offerEndDate)

        val updateTime: () -> Unit = {
            val currentDate = Date()
            val remainingMillis = maxOf(0, endDate.time - currentDate.time)
            remainingTime = formatMillisToTime(remainingMillis)
        }

        val timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                updateTime()
            }
        }, 0, 1000)


    }

    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.05f))
            .padding(PaddingValues(horizontal = AppTheme.dimens.paddingHorizontal))
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Text(
            modifier = Modifier.weight(2f),
            text = stringResource(R.string.there_are) + productDetails.itemsLeft + stringResource(R.string.items_left_hurry_up_to_order),
            overflow = TextOverflow.Clip,
            maxLines = 2,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.secondary
            ),
        )
        SpacerWidthMedium()
        Column(
            modifier = Modifier.weight(0.5f),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                modifier = Modifier,
                text = remainingTime.split(",")[0],
                overflow = TextOverflow.Clip,
                maxLines = 1,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.onTertiary
                ),
            )
            Text(
                modifier = Modifier,
                text = stringResource(R.string.day),
                overflow = TextOverflow.Clip,
                maxLines = 1,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)
                ),
            )
        }
        Text(
            modifier = Modifier.weight(0.2f),
            text = ":",
            overflow = TextOverflow.Clip,
            maxLines = 1,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.onTertiary
            ),
        )
        Column(
            modifier = Modifier.weight(0.5f),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                modifier = Modifier,
                text = remainingTime.split(",")[1],
                overflow = TextOverflow.Clip,
                maxLines = 1,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.onTertiary
                ),
            )
            Text(
                modifier = Modifier,
                text = stringResource(R.string.hour),
                overflow = TextOverflow.Clip,
                maxLines = 1,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)
                ),
            )
        }
        Text(
            modifier = Modifier.weight(0.2f),
            text = ":",
            overflow = TextOverflow.Clip,
            maxLines = 1,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.onTertiary
            ),
        )
        Column(
            modifier = Modifier.weight(0.5f),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                modifier = Modifier,
                text = remainingTime.split(",")[2],
                overflow = TextOverflow.Clip,
                maxLines = 1,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.onTertiary
                ),
            )
            Text(
                modifier = Modifier,
                text = stringResource(R.string.min),
                overflow = TextOverflow.Clip,
                maxLines = 1,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)
                ),
            )
        }
    }
}

fun formatMillisToTime(millis: Long): String {
    val days = TimeUnit.MILLISECONDS.toDays(millis)
    val hours = TimeUnit.MILLISECONDS.toHours(millis) % 24
    val minutes = TimeUnit.MILLISECONDS.toMinutes(millis) % 60

    val formattedHours = hours.toString().padStart(2, '0')
    val formattedMinutes = minutes.toString().padStart(2, '0')

    return "$days, $formattedHours, $formattedMinutes"
}


@Composable
fun calculateRemainingTime(offerEndDate: String): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    val currentDate = Date()

    val endDate = dateFormat.parse(offerEndDate)
    val remainingTimeMillis = maxOf(0, endDate.time - currentDate.time)

    val days = TimeUnit.MILLISECONDS.toDays(remainingTimeMillis)
    val hours = TimeUnit.MILLISECONDS.toHours(remainingTimeMillis) % 24
    val minutes = TimeUnit.MILLISECONDS.toMinutes(remainingTimeMillis) % 60

    return "$days, $hours, $minutes"
}
