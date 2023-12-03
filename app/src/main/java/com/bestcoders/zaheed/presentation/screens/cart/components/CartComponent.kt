package com.bestcoders.zaheed.presentation.screens.cart.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.extentions.checkTheActualTimeAtGivenTime
import com.bestcoders.zaheed.core.extentions.getCurrentDayName
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.domain.model.products.Cart
import com.bestcoders.zaheed.presentation.components.CartProductComponent
import com.bestcoders.zaheed.presentation.components.LineDivider
import com.bestcoders.zaheed.presentation.components.MainPrice
import com.bestcoders.zaheed.presentation.components.SpacerHeightMedium
import com.bestcoders.zaheed.presentation.components.SpacerWidthMediumLarge
import com.bestcoders.zaheed.presentation.components.SpacerWidthSmall
import com.bestcoders.zaheed.ui.theme.AppTheme
import org.joda.time.LocalDate

@Composable
fun CartComponent(
    modifier: Modifier = Modifier,
    cart: Cart,
    onCartClick: (Int) -> Unit,
    onProductClick: (Int) -> Unit
) {
    val lazyListState = rememberLazyListState()
    val storeTime = remember {
        derivedStateOf {
            val filteredList = cart.workingHours.filter {
                it.day.equals(
                    LocalDate().getCurrentDayName(),
                    ignoreCase = true
                )
            }
            if (filteredList.isNotEmpty()) {
                filteredList[0]
            } else {
                null
            }
        }
    }


    val isStoreOpen =
        if (storeTime.value?.phase1from != null &&
            storeTime.value!!.phase1from.isNotEmpty()
        ) {
            LocalDate().checkTheActualTimeAtGivenTime(
                startTime = storeTime.value!!.phase1from,
                endTime = storeTime.value!!.phase1to
            ) || LocalDate().checkTheActualTimeAtGivenTime(
                startTime = storeTime.value!!.phase2from,
                endTime = storeTime.value!!.phase2to
            )
        } else {
            false
        }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppTheme.dimens.paddingHorizontal),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .width(AppTheme.dimens.storeWithProductsStoreImageWidth)
                    .height(AppTheme.dimens.storeWithProductsStoreImageHeight)
                    .clip(RoundedCornerShape(Constants.CORNER_RADUIES)),
                contentScale = ContentScale.Inside,
                model = cart.logo,
                contentDescription = null
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.small)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        content = {
                            Text(
                                modifier = Modifier,
                                text = cart.name,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.secondary
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                modifier = Modifier,
                                text = if (isStoreOpen) {
                                    stringResource(id = R.string.opened)
                                } else {
                                    stringResource(id = R.string.closed)
                                },
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontWeight = FontWeight.Normal,
                                    textAlign = TextAlign.Start,
                                    color = if (isStoreOpen) {
                                        MaterialTheme.colorScheme.onSecondary
                                    } else {
                                        MaterialTheme.colorScheme.onPrimary
                                    },
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    )
                    SpacerWidthSmall()
                    Text(
                        modifier = Modifier.clickable { onCartClick(cart.id) },
                        text = stringResource(R.string.show),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.End
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Clip,
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = cart.address,
                        maxLines = 1,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Start,
                            color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)
                        ),
                    )
                    Text(
                        text = " (" + cart.distance + stringResource(id = R.string.km) + ")" ,
                        maxLines = 1,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Start,
                            color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)
                        ),
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    MainPrice(
                        price = cart.cartTotalBeforeDiscount.toString(),
                        textStyle = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.secondary
                        ),
                    )
                    Row(
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.05f),
                                shape = RoundedCornerShape(Constants.CORNER_RADUIES)
                            )
                            .padding(horizontal = AppTheme.dimens.smallMedium),
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.you_saved),
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.secondary
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        MainPrice(
                            price = cart.cartTotalSaving.toString(),
                            textStyle = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSecondary
                            ),
                        )
                    }
                }
            }

        }
        SpacerHeightMedium()
        if (cart.products.isNotEmpty()) {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                state = lazyListState,
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Top,
                content = {
                    items(
                        count = cart.products.size,
                        key = {
                            cart.products[it].hashCode()
                        }
                    ) { index ->
                        SpacerWidthMediumLarge()
                        CartProductComponent(
                            modifier = Modifier.wrapContentHeight(),
                            product = cart.products[index],
                            onProductClick = onProductClick
                        )
                        if (index == cart.products.size - 1) {
                            SpacerWidthMediumLarge()
                        }
                    }
                },
            )
        }
        SpacerHeightMedium()
        LineDivider(modifier = Modifier.padding(bottom = AppTheme.dimens.medium))
    }
}

