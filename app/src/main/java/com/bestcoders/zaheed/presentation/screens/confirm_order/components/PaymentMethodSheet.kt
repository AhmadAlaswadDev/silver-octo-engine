package com.bestcoders.zaheed.presentation.screens.confirm_order.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.presentation.components.SpacerHeightMedium
import com.bestcoders.zaheed.ui.theme.AppTheme

@Composable
fun PaymentMethodSheet(
    modifier: Modifier = Modifier,
    showPaymentMethodSelector: Boolean = true,
    onChoosePaymentClick: () -> Unit = {}
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
                text = stringResource(R.string.payment_method),
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.primary,
                ),
            )
            if (showPaymentMethodSelector) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(AppTheme.dimens.paymentMethodRowConfirmOrderHeight)
                        .background(
                            color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.08f),
                            shape = RoundedCornerShape(
                                Constants.CORNER_RADUIES
                            )
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    content = {
                        Image(
                            modifier = Modifier
                                .weight(0.4f)
                                .size(AppTheme.dimens.moneyIconConfirmOrderSize),
                            painter = painterResource(id = R.drawable.visa_icon),
                            contentScale = ContentScale.Inside,
                            contentDescription = null,
                        )
                        Text(
                            modifier = Modifier
                                .weight(1f)
                                .wrapContentHeight(),
                            text = stringResource(R.string.online_payment),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Start,
                                color = MaterialTheme.colorScheme.primary,
                            ),
                        )
                        TextButton(
                            modifier = Modifier.weight(0.5f),
                            onClick = onChoosePaymentClick,
                            content = {
                                Text(
                                    modifier = Modifier.wrapContentHeight(),
                                    text = stringResource(R.string.change),
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontWeight = FontWeight.SemiBold,
                                        textAlign = TextAlign.Start,
                                        color = MaterialTheme.colorScheme.onTertiary,
                                    ),
                                )
                            },
                        )
                    },
                )
            } else {
                Text(
                    modifier = Modifier
                        .padding(horizontal = AppTheme.dimens.paddingHorizontal)
                        .wrapContentHeight(),
                    text = stringResource(id = R.string.online_payment),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.8f),
                    ),
                )
            }
            SpacerHeightMedium()
        },
    )
}
