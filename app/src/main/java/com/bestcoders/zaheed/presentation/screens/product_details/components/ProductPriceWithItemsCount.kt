package com.bestcoders.zaheed.presentation.screens.product_details.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.presentation.components.SpacerWidthMediumLarge
import com.bestcoders.zaheed.ui.theme.AppTheme

@Composable
fun ProductPriceWithItemsCount(
    mainPrice: String,
    strokedPrice: String,
    quantity: MutableState<Int>,
    isAvailableVariantProp: Boolean,
) {
    Row(
        modifier = Modifier
            .padding(PaddingValues(horizontal = AppTheme.dimens.paddingHorizontal))
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .wrapContentWidth()
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier,
                text = strokedPrice.replace(",", "."),
                overflow = TextOverflow.Clip,
                maxLines = 1,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                    textDecoration = TextDecoration.LineThrough,
                ),
            )
            SpacerWidthMediumLarge()
            Text(
                modifier = Modifier,
                text = mainPrice.replace(",", "."),
                overflow = TextOverflow.Clip,
                maxLines = 1,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.onPrimary,
                ),
            )
            Text(
                modifier = Modifier.align(Alignment.Bottom),
                text = " " + Constants.settings.defaultCurrency.symbol,
                overflow = TextOverflow.Clip,
                maxLines = 1,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.onPrimary
                ),
            )
        }
        Row(
            modifier = Modifier
                .wrapContentWidth()
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    if (quantity.value > 1 && isAvailableVariantProp) {
                        quantity.value = quantity.value - 1
                    }
                }) {
                Icon(
                    modifier = Modifier.size(AppTheme.dimens.increaseDecreaseItemsIconProductDetailsSize),
                    painter = painterResource(id = R.drawable.minus_border_icon),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
            Text(
                modifier = Modifier,
                text = quantity.value.toString(),
                overflow = TextOverflow.Clip,
                maxLines = 1,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.primary
                ),
            )
            IconButton(
                onClick = {
                    if (isAvailableVariantProp) {
                        quantity.value = quantity.value + 1
                    }
                }) {
                Image(
                    modifier = Modifier.size(AppTheme.dimens.increaseDecreaseItemsIconProductDetailsSize),
                    painter = painterResource(id = R.drawable.add_icon),
                    contentDescription = null,
                )
            }
        }
    }
}