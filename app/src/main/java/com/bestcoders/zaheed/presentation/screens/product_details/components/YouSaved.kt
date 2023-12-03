package com.bestcoders.zaheed.presentation.screens.product_details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.extentions.removeZerosAfterComma
import com.bestcoders.zaheed.core.extentions.roundTo1DecimalPlace
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.ui.theme.AppTheme

@Composable
fun YouSaved(saved: Double) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.05f))
            .padding(PaddingValues(horizontal = AppTheme.dimens.paddingHorizontal))
            .fillMaxWidth()
            .height(AppTheme.dimens.youSavedRowProductDetailsHeight),
        horizontalArrangement = Arrangement.spacedBy(40.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier,
            text = stringResource(R.string.you_saved),
            overflow = TextOverflow.Clip,
            maxLines = 1,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.secondary
            ),
        )
        Row(
            modifier = Modifier
                .wrapContentWidth()
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(AppTheme.dimens.coinImageProductDetailsSize),
                painter = painterResource(id = R.drawable.coin_icon),
                tint = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.7f),
                contentDescription = null
            )
            Text(
                modifier = Modifier,
                text = saved.roundTo1DecimalPlace().toString().removeZerosAfterComma().replace(",", "."),
                overflow = TextOverflow.Clip,
                maxLines = 1,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.7f)
                ),
            )
            Text(
                modifier = Modifier,
                text = " " + Constants.settings.defaultCurrency.symbol,
                overflow = TextOverflow.Clip,
                maxLines = 1,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.7f)
                ),
            )
        }
    }
}