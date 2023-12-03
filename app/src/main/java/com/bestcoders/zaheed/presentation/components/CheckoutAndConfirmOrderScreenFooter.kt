package com.bestcoders.zaheed.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.extentions.removePadding
import com.bestcoders.zaheed.core.extentions.removeZerosAfterComma
import com.bestcoders.zaheed.domain.model.products.Summary
import com.bestcoders.zaheed.ui.theme.AppTheme


@Composable
fun CheckoutAndConfirmOrderScreenFooter(
    buttonLabel: String,
    summary: Summary,
    onContinueButtonClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .padding(
                start = AppTheme.dimens.paddingHorizontal,
                end = AppTheme.dimens.paddingHorizontal,
                bottom = AppTheme.dimens.paddingVertical
            ),
        verticalArrangement = Arrangement.SpaceEvenly,
        content = {
            LineDivider(modifier = Modifier.removePadding(-AppTheme.dimens.paddingHorizontal))
            OrderDiscountCheckoutAndConfirmOrderComponent(
                beforeDiscount = summary.cartTotalBeforeDiscount.toString().removeZerosAfterComma(),
                totalDiscountAmount = summary.cartTotalDiscountAmount.toString().removeZerosAfterComma()
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                content = {
                    Text(
                        modifier = Modifier.wrapContentHeight(),
                        text = stringResource(R.string.total),
                        overflow = TextOverflow.Clip,
                        maxLines = 1,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Start,
                            color = MaterialTheme.colorScheme.primary
                        ),
                    )
                    MainPrice(
                        price = summary.cartTotal.toString(), textStyle = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Start,
                            color =  MaterialTheme.colorScheme.primary,
                        )
                    )
                },
            )
            PrimaryButton(
                text = buttonLabel,
                radius = 50f,
                onClick = onContinueButtonClick
            )
        }
    )
}