package com.bestcoders.zaheed.presentation.screens.confirm_order.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.extentions.formatDate
import com.bestcoders.zaheed.core.extentions.formatTime
import com.bestcoders.zaheed.core.extentions.removePadding
import com.bestcoders.zaheed.presentation.components.LineDivider
import com.bestcoders.zaheed.ui.theme.AppTheme
import com.bestcoders.zaheed.ui.theme.CustomColor

@Composable
fun PickupDateTimeSelectorComponent(
    modifier: Modifier = Modifier,
    showSelector: MutableState<Boolean>,
    showDateTimePicker: MutableState<Boolean>,
    selectedOption: MutableState<Int>,
    dateText: MutableState<String>,
    timeText: MutableState<String>,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.Start,
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                content = {
                    Text(
                        modifier = Modifier.fillMaxWidth().wrapContentHeight()
                            .weight(1f),
                        text = stringResource(R.string.pickup_time),
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Start,
                            color = MaterialTheme.colorScheme.primary,
                        ),
                    )
                    if (!showSelector.value) {
                        Icon(
                            modifier = Modifier
                                .size(AppTheme.dimens.editIconConfirmOrderSize)
                                .weight(0.1f)
                                .clickable {
                                    showSelector.value = true
                                    selectedOption.value = 0
                                    dateText.value = ""
                                    timeText.value = ""
                                },
                            painter = painterResource(id = R.drawable.edit_icon),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onTertiary,
                        )
                    }
                },
            )
            if (showSelector.value || dateText.value.isEmpty() || timeText.value.isEmpty()) {
                RadioButtonOption(
                    index = 0,
                    isSelected = selectedOption.value == 0,
                    onClick = {
                        selectedOption.value = it
                        showDateTimePicker.value = false
                    },
                    iconRes = R.drawable.success_icon,
                    text = stringResource(R.string.for_now)
                )

                RadioButtonOption(
                    index = 1,
                    isSelected = selectedOption.value == 1,
                    onClick = {
                        selectedOption.value = it
                        showDateTimePicker.value = true
                    },
                    iconRes = R.drawable.success_icon,
                    text = stringResource(R.string.choose_the_pickup_time),
                    )
                LineDivider(modifier = Modifier.removePadding(-AppTheme.dimens.paddingHorizontal))
            }
            else {
                Text(
                    modifier = Modifier
                        .padding(horizontal = AppTheme.dimens.large)
                        .wrapContentHeight(),
                    text = if(dateText.value.isNotEmpty()){formatDate(dateText.value)}else{dateText.value},
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f),
                    ),
                )
                Text(
                    modifier = Modifier
                        .padding(horizontal = AppTheme.dimens.large)
                        .wrapContentHeight(),
                    text = if(timeText.value.isNotEmpty()){
                        formatTime(timeText.value)
                    }else{timeText.value},
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f),
                    ),
                )
                LineDivider(modifier = Modifier.removePadding(-AppTheme.dimens.paddingHorizontal))
            }
        },
    )
}

@Composable
fun RadioButtonOption(
    index: Int,
    isSelected: Boolean,
    iconRes: Int,
    text: String,
    onClick: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { onClick(index) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        content = {
            if (isSelected) {
                Box(
                    modifier = Modifier
                        .size(AppTheme.dimens.pickupTimeBoxConfirmOrderSize)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.onTertiary)
                        .border(
                            border = BorderStroke(
                                2.dp,
                                MaterialTheme.colorScheme.onTertiary
                            ),
                        )
                        .padding(5.dp),
                    content = {
                        Icon(
                            modifier = Modifier.fillMaxSize(),
                            painter = painterResource(id = iconRes),
                            contentDescription = null,
                            tint = CustomColor.White,
                        )
                    }
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(AppTheme.dimens.pickupTimeBoxConfirmOrderSize)
                        .border(
                            border = BorderStroke(
                                2.dp,
                                MaterialTheme.colorScheme.onTertiary
                            ),
                            shape = CircleShape
                        )
                        .padding(5.dp),
                )
            }
            Text(
                modifier = Modifier.wrapContentHeight(),
                text = text,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.onTertiary
                )
            )
        },
    )
}
