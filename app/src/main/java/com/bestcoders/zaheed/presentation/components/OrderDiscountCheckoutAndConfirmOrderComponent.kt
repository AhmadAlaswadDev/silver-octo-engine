package com.bestcoders.zaheed.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
fun OrderDiscountCheckoutAndConfirmOrderComponent(
    beforeDiscount: String,
    totalDiscountAmount: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.Start,
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                content = {
                    Text(
                        modifier = Modifier.wrapContentHeight(),
                        text = stringResource(R.string.before_discount),
                        overflow = TextOverflow.Clip,
                        maxLines = 1,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Start,
                            color = MaterialTheme.colorScheme.primary
                        ),
                    )
                    MainPrice(
                        price = beforeDiscount, textStyle = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Start,
                            color =  MaterialTheme.colorScheme.primary,
                        )
                    )
                },
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                content = {
                    Text(
                        modifier = Modifier.wrapContentHeight(),
                        text = stringResource(R.string.total_discount_amount),
                        overflow = TextOverflow.Clip,
                        maxLines = 1,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Start,
                            color = MaterialTheme.colorScheme.onSecondary
                        ),
                    )
                    MainPrice(
                        price = totalDiscountAmount, textStyle = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Start,
                            color =  MaterialTheme.colorScheme.onSecondary,
                        )
                    )
                },
            )
            LineDivider(modifier = Modifier.removePadding(-AppTheme.dimens.paddingHorizontal))
        },
    )
}