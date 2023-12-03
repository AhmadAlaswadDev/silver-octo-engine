package com.bestcoders.zaheed.presentation.screens.profile_screens.order_history.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.extentions.color
import com.bestcoders.zaheed.core.extentions.removePadding
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.domain.model.track.Order
import com.bestcoders.zaheed.presentation.components.CopyTextButton
import com.bestcoders.zaheed.presentation.components.LineDivider
import com.bestcoders.zaheed.presentation.components.MainPrice
import com.bestcoders.zaheed.presentation.components.SpacerWidthMediumLarge
import com.bestcoders.zaheed.presentation.components.SpacerWidthSmall
import com.bestcoders.zaheed.ui.theme.AppTheme

@Composable
fun OrderComponent(order: Order, onMoreDerailsClick: (orderId:Int) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(AppTheme.dimens.orderHistoryItemHeight)
            .border(
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f)),
                shape = RoundedCornerShape(Constants.CORNER_RADUIES.dp)
            )
            .padding(
                horizontal = AppTheme.dimens.paddingHorizontal,
                vertical = AppTheme.dimens.large,
            ),
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.small),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            // order status
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween,
                content = {
                    Text(
                        text = order.orderStatusString,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = order.orderStatusColor.color,
                            fontWeight = FontWeight.SemiBold,
                        )
                    )
                    Text(
                        text = order.date,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.secondary.copy(0.5f),
                            fontWeight = FontWeight.SemiBold,
                        )
                    )
                }
            )
            // track order
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween,
                content = {
                    Text(
                        text = stringResource(id = R.string.track_order),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.secondary.copy(0.5f),
                            fontWeight = FontWeight.SemiBold,
                        )
                    )
                    Row(
                        modifier = Modifier.wrapContentHeight(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = order.code,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                        CopyTextButton(copyText = order.code)
                    }
                }
            )
            // Store info
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.mediumLarge)
            ) {
                AsyncImage(
                    modifier = Modifier
                        .width(AppTheme.dimens.storeInfoOrderHistoryImageWidth)
                        .height(AppTheme.dimens.storeInfoOrderHistoryImageHeight)
                        .clip(RoundedCornerShape(Constants.CORNER_RADUIES)),
                    contentScale = ContentScale.Inside,
                    model = order.store.logo,
                    contentDescription = null
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.small)
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = order.store.name,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.secondary
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Clip
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = order.store.address,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.secondary
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Clip
                    )
                }

            }
            LineDivider(Modifier.removePadding(-AppTheme.dimens.paddingHorizontal))
            // Products
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .removePadding(-AppTheme.dimens.paddingHorizontal),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Start,
                content = {
                    order.store.products?.forEachIndexed { index, product ->
                        SpacerWidthMediumLarge()
                        AsyncImage(
                            modifier = Modifier
                                .size(AppTheme.dimens.productImageOrderHistorySize)
                                .clip(RoundedCornerShape(Constants.CORNER_RADUIES)),
                            contentScale = ContentScale.Inside,
                            model = product.thumbnailImage,
                            contentDescription = null
                        )
                        if (index == order.store.products.size - 1) {
                            SpacerWidthMediumLarge()
                        }
                    }
                },
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                content = {
                    // You saved
                    Row(
                        modifier = Modifier.wrapContentSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        content = {
                            Text(
                                text = stringResource(id = R.string.you_saved),
                                maxLines = 1,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    textAlign = TextAlign.Start,
                                    color = MaterialTheme.colorScheme.primary,
                                ),
                            )
                            SpacerWidthSmall()
                            MainPrice(
                                price = order.totalSaving.toString(),
                                textStyle = MaterialTheme.typography.bodySmall.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    textAlign = TextAlign.Start,
                                    color = MaterialTheme.colorScheme.onSecondary,
                                )
                            )
                        },
                    )
                    // Total
                    Row(
                        modifier = Modifier.wrapContentSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.small),
                        content = {
                            Text(
                                modifier = Modifier.wrapContentHeight(),
                                text = stringResource(R.string.total),
                                overflow = TextOverflow.Clip,
                                maxLines = 1,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    textAlign = TextAlign.Start,
                                    color = MaterialTheme.colorScheme.primary
                                ),
                            )
                            MainPrice(
                                price = order.grandTotal.toString(),
                                textStyle = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Start,
                                    color = MaterialTheme.colorScheme.primary,
                                )
                            )
                        },
                    )
                },
            )
            LineDivider(Modifier.removePadding(-AppTheme.dimens.paddingHorizontal))
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                content = {
                    Text(
                        text = stringResource(id = R.string.more_details),
                        maxLines = 1,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Start,
                            color = MaterialTheme.colorScheme.onPrimary,
                        ),
                    )
                    Icon(
                        modifier = Modifier
                            .clickable {
                                onMoreDerailsClick(order.combinedOrderId)
                            }
                            .size(AppTheme.dimens.navigationBarItemIconSize)
                            .graphicsLayer(
                                rotationY = when (Constants.DEFAULT_LANGUAGE) {
                                    Constants.SAUDI_LANGUAGE_CODE -> 180f
                                    else -> 0f
                                }
                            ),
                        imageVector = Icons.Rounded.ArrowForward,
                        contentDescription = null
                    )
                },
            )
        }
    )
}