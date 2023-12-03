package com.bestcoders.zaheed.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.extentions.removeZerosAfterComma
import com.bestcoders.zaheed.core.extentions.roundTo1DecimalPlace
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.ui.theme.AppTheme

@Composable
fun YouSavedBordered(saved: Double) {
    Row(
        modifier = Modifier
            .height(AppTheme.dimens.youSavedRowPaymentSuccessHeight)
            .border(
                border = BorderStroke(
                    width = 0.5.dp,
                    color = MaterialTheme.colorScheme.onSecondary
                ),
                shape = RoundedCornerShape(Constants.CORNER_RADUIES.dp)
            )
            .background(
                color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.05f),
                shape = RoundedCornerShape(Constants.CORNER_RADUIES.dp)
            )
            .padding(horizontal = 30.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        content = {
            Text(
                text = stringResource(id = R.string.you_saved),
                maxLines = 1,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.secondary
                ),
            )
            Icon(
                modifier = Modifier.size(AppTheme.dimens.coinPaymentSuccessSize),
                painter = painterResource(id = R.drawable.coin_icon),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSecondary
            )
            Text(
                text = saved.roundTo1DecimalPlace().toString()
                    .removeZerosAfterComma().replace(",", ".") + Constants.MAIN_CURENCY,
                maxLines = 1,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.onSecondary
                ),
            )
        },
    )
}