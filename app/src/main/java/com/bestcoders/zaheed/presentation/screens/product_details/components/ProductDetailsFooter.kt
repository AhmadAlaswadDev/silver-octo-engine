package com.bestcoders.zaheed.presentation.screens.product_details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import com.bestcoders.zaheed.ui.theme.CustomColor

@Composable
fun ProductDetailsFooter(
    productPrice: Double,
    onAddToCartButtonClick: () -> Unit,
    quantity: MutableState<Int>
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(AppTheme.dimens.boxFooterProductDetailsHeight)
        .background(color = CustomColor.White),
        contentAlignment = Alignment.Center,
        content = {
            Button(
                modifier = Modifier
                    .height(AppTheme.dimens.addToCartButtonHeight)
                    .fillMaxWidth()
                    .padding(horizontal = AppTheme.dimens.paddingHorizontal),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onPrimary,
                    disabledContainerColor = MaterialTheme.colorScheme.onPrimary,
                ),
                onClick = onAddToCartButtonClick,
                content = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier,
                                text = (productPrice * quantity.value).roundTo1DecimalPlace().toString()
                                    .removeZerosAfterComma().replace(",", "."),
                                overflow = TextOverflow.Clip,
                                maxLines = 1,
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Start,
                                    color = CustomColor.White,
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
                                    color = CustomColor.White
                                ),
                            )
                        }
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(AppTheme.dimens.addToCartButtonBoxIconSize)
                                    .background(
                                        color = CustomColor.White,
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center,
                                content = {
                                    Icon(
                                        modifier = Modifier.size(AppTheme.dimens.addToCartButtonIconSize),
                                        painter = painterResource(id = R.drawable.cart_selected_icon),
                                        contentDescription = null,
                                    )
                                }
                            )
                            Text(
                                text = stringResource(R.string.add_to_cart),
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    color = CustomColor.White,
                                    fontWeight = FontWeight.Bold,
                                ),
                            )
                        }
                    }
                }
            )
        }
    )
}