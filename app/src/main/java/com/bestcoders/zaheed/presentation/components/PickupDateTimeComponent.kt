package com.bestcoders.zaheed.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.extentions.removePadding
import com.bestcoders.zaheed.ui.theme.AppTheme

@Composable
fun PickupDateTimeComponent(
    modifier: Modifier = Modifier,
    pickupDate: String,
    pickupTime: String,
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.Start,
        content = {
            Text(
                modifier = Modifier.wrapContentHeight(),
                text = stringResource(R.string.pickup_time),
                overflow = TextOverflow.Clip,
                maxLines = 1,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.primary
                ),
            )
            if(pickupDate.isEmpty() && pickupTime.isEmpty()){
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = AppTheme.dimens.paddingHorizontal),
                    text = stringResource(R.string.now),
                    maxLines = 1,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colorScheme.secondary.copy(0.5f),
                    ),
                )
            }else{
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = AppTheme.dimens.paddingHorizontal),
                    text =  pickupDate,
                    maxLines = 1,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colorScheme.secondary.copy(0.5f),
                    ),
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = AppTheme.dimens.paddingHorizontal),
                    text = pickupTime,
                    maxLines = 1,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colorScheme.secondary.copy(0.5f),
                    ),
                )
            }
            LineDivider(modifier = Modifier.removePadding(-AppTheme.dimens.paddingHorizontal))
        }
    )
}
