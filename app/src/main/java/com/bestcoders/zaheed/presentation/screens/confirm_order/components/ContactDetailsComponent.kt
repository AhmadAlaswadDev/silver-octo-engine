package com.bestcoders.zaheed.presentation.screens.confirm_order.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.extentions.removePadding
import com.bestcoders.zaheed.presentation.components.LineDivider
import com.bestcoders.zaheed.ui.theme.AppTheme

@Composable
fun ContactDetailsComponent(
    modifier: Modifier = Modifier,
    name: String,
    phoneNumber: String,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        content = {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                text = stringResource(R.string.contact_details),
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.primary,
                ),
            )
            Text(
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(horizontal = AppTheme.dimens.large),
                text = name,
                maxLines = 1,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.secondary.copy(0.5f),
                ),
            )
            Text(
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(horizontal = AppTheme.dimens.large),
                text = phoneNumber,
                maxLines = 1,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.secondary.copy(0.5f),
                ),
            )
            LineDivider(modifier = Modifier.removePadding(-AppTheme.dimens.paddingHorizontal))
        },
    )
}


