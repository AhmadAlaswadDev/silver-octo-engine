package com.bestcoders.zaheed.presentation.screens.confirm_order.components

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.extentions.removePadding
import com.bestcoders.zaheed.core.extentions.toJson
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.domain.model.products.Product
import com.bestcoders.zaheed.presentation.components.LineDivider
import com.bestcoders.zaheed.presentation.components.MainPrice
import com.bestcoders.zaheed.presentation.components.SpacerWidthMediumLarge
import com.bestcoders.zaheed.presentation.components.SpacerWidthSmall
import com.bestcoders.zaheed.presentation.components.StrokedPrice
import com.bestcoders.zaheed.presentation.screens.home.components.DiscountBadge
import com.bestcoders.zaheed.ui.theme.AppTheme

@Composable
fun ConfirmOrderProductComponent(
    modifier: Modifier = Modifier,
    product: Product
) {

    Log.e("ASFIHASFIOH", "ConfirmOrderProductComponent: ${product.toJson()}", )

    val sidePadding = (-AppTheme.dimens.paddingHorizontal)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        content = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                content = {
                    Card(
                        modifier = Modifier
                            .size(AppTheme.dimens.productImageCheckoutSize),
                        shape = RoundedCornerShape(Constants.CORNER_RADUIES.dp),
                        border = BorderStroke(
                            2.dp,
                            MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f)
                        ),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(
                                alpha = 0.1f
                            ),
                            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer.copy(
                                alpha = 0.1f
                            ),
                        ),
                        content = {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                content = {
                                    AsyncImage(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .zIndex(1f),
                                        model = product.thumbnailImage,
                                        contentScale = ContentScale.Crop,
                                        contentDescription = null
                                    )
                                    DiscountBadge(
                                        modifier = Modifier
                                            .offset(x = 8.dp, y = 8.dp)
                                            .height(AppTheme.dimens.discountBadgeHeight)
                                            .wrapContentWidth()
                                            .zIndex(3f),
                                        label = product.discount.toString()
                                    )
                                },
                            )
                        },
                    )
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Top,
                        content = {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 5.dp),
                                text = product.name,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    textAlign = TextAlign.Start,
                                    color = MaterialTheme.colorScheme.primary,
                                ),
                            )
                            val productChoices = StringBuffer()
                            if (!product.cartColor?.name.isNullOrEmpty()) {
                                productChoices.append(stringResource(R.string.color_with) + product.cartColor!!.name + "\n")
                            }
                            if (!product.cartChoices.isNullOrEmpty()) {
                                product.cartChoices.forEach {
                                    productChoices.append(
                                        it.name + ": " + it.cartValue?.name + "\n"
                                    )
                                }
                                productChoices.append(stringResource(R.string.the_quantity) + ": " +product.quantity.toString())
                            }
                            if(!productChoices.isNullOrEmpty()){
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 5.dp),
                                    text = productChoices.toString(),
                                    overflow = TextOverflow.Clip,
                                    lineHeight = 20.sp,
                                    minLines = 1,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontWeight = FontWeight.SemiBold,
                                        textAlign = TextAlign.Start,
                                        color = MaterialTheme.colorScheme.secondary.copy(
                                            alpha = 0.5f
                                        ),
                                    ),
                                )
                            }
                        },
                    )
                },
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                content = {
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
                                price = product.saved.toString(),
                                textStyle = MaterialTheme.typography.bodySmall.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    textAlign = TextAlign.Start,
                                    color = MaterialTheme.colorScheme.onSecondary,
                                )
                            )
                        },
                    )
                    Row(
                        modifier = Modifier.wrapContentSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End,
                        content = {
                            StrokedPrice(price = product.strokedPrice)
                            SpacerWidthMediumLarge()
                            MainPrice(
                                price = product.mainPrice,
                                textStyle = MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Start,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                )
                            )
                        },
                    )

                },
            )
            LineDivider(modifier = Modifier.removePadding(sidePadding))
        },
    )
}