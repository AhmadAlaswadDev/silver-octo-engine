package com.bestcoders.zaheed.presentation.screens.filter.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.presentation.components.SpacerHeightLarge
import com.bestcoders.zaheed.ui.theme.AppTheme
import com.bestcoders.zaheed.ui.theme.CustomColor
import com.bestcoders.zaheed.ui.theme.rememberWindowSizeClass
import kotlin.math.roundToInt


@Composable
fun PriceRangeSlider(
    minValue: Int,
    maxValue: Int,
    onPriceRangeChanged: (min: Int, max: Int) -> Unit,
    priceRange: MutableState<ClosedFloatingPointRange<Float>>
) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.wrapContentSize(),
                text = stringResource(R.string.price_range),
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Start
                ),
            )
            Text(
                modifier = Modifier.wrapContentSize(),
                text = "$minValue-$maxValue",
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f),
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.End
                ),
            )
        }
        SpacerHeightLarge()
        SpacerHeightLarge()
        Box(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
        ) {
            // Min Text
            Box(
                modifier = Modifier
                    .padding(5.dp)
                    .absoluteOffset(x = calculateThumbPosition(priceRange.value.start), y = (-30).dp),
            ) {
                Text(
                    modifier = Modifier.fillMaxSize(),
                    text = priceRange.value.start.roundToInt()
                        .toString() + " " + Constants.settings.defaultCurrency.symbol,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.SemiBold
                    ),
                )
            }
            Box(
                modifier = Modifier
                    .padding(5.dp)
                    .absoluteOffset(
                        x = calculateThumbPosition(priceRange.value.endInclusive),
                        y = (-30).dp
                    ),
            ) {
                // Max Text
                Text(
                    modifier = Modifier.fillMaxSize(),
                    text = priceRange.value.endInclusive.roundToInt()
                        .toString() + " " + Constants.settings.defaultCurrency.symbol,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.SemiBold
                    ),
                )
            }
            // RangeSlider
            RangeSlider(
                modifier = Modifier.fillMaxWidth(),
                valueRange = minValue.toFloat()..maxValue.toFloat(),
                value = priceRange.value,
                onValueChange = { newRange ->
                    priceRange.value = newRange
                },
                onValueChangeFinished = {
                    onPriceRangeChanged(
                        priceRange.value.start.roundToInt(),
                        priceRange.value.endInclusive.roundToInt()
                    )
                },
                colors = SliderDefaults.colors(
                    activeTickColor = Color.Red,
                    activeTrackColor = MaterialTheme.colorScheme.onPrimary,
                    thumbColor = CustomColor.White,
                    inactiveTickColor = Color.Gray,
                    inactiveTrackColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.2f),
                ),
            )

        }
    }
}

@Composable
private fun calculateThumbPosition(value: Float): Dp {
    val window = rememberWindowSizeClass()
    val sliderWidth = window.width.size - AppTheme.dimens.sliderWidthMinus.value
    val sliderRange = 1000f
    return if (Constants.DEFAULT_LANGUAGE == Constants.SAUDI_LANGUAGE_CODE) {
        (-value / sliderRange * sliderWidth).dp
    } else {
        (value / sliderRange * sliderWidth).dp
    }
}
