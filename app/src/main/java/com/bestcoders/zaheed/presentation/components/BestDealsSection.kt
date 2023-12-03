package com.bestcoders.zaheed.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.extentions.removeZerosAfterComma
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.domain.model.products.BestSale
import com.bestcoders.zaheed.presentation.screens.home.components.DiscountBadge
import com.bestcoders.zaheed.ui.theme.AppTheme
import com.bestcoders.zaheed.ui.theme.CustomColor

@Stable
@Composable
fun BestDealsSection(
    modifier: Modifier = Modifier,
    list: List<BestSale>,
    onFavoriteClick: (Int, Boolean) -> Unit,
    onProductClick: (Int) -> Unit,
) {
    if (list.isNotEmpty()) {
        Column(modifier = modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = AppTheme.dimens.paddingHorizontal,
                    ),
                text = stringResource(R.string.best_deals),
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
            )
            SpacerHeightMedium()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Top
            ) {
                list.forEachIndexed { index, bestSale ->
                    SpacerWidthMediumLarge()
                    BestSalesItem(
                        modifier = Modifier,
                        bestSale = bestSale,
                        onFavoriteClick = onFavoriteClick,
                        onProductClick = onProductClick
                    )
                    if (index == list.size - 1) {
                        SpacerWidthMediumLarge()
                    }
                }
            }
        }
    }
}


@Composable
fun BestSalesItem(
    modifier: Modifier = Modifier,
    bestSale: BestSale,
    onFavoriteClick: (Int, Boolean) -> Unit,
    onProductClick: (Int) -> Unit,
) {
    val isFavorite = remember {
        mutableStateOf(bestSale.isFavorite)
    }
    Card(
        modifier = modifier
            .width(AppTheme.dimens.bestSaleItemWidth)
            .wrapContentHeight()
            .background(CustomColor.White)
            .shadow(
                elevation = 15.dp,
                shape = RoundedCornerShape(10.dp),
                ambientColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
            )
            .clickable { onProductClick(bestSale.id) },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = CustomColor.White,
            disabledContainerColor = CustomColor.White,
        ),
    ) {
        Card(
            modifier = Modifier.height(AppTheme.dimens.bestSaleItemHeight),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = CustomColor.White,
                disabledContainerColor = CustomColor.White,
            )
        ) {
            Box(
                modifier = Modifier
                    .background(CustomColor.White)
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .zIndex(1f),
                    contentAlignment = Alignment.Center,
                ) {
                    AsyncImage(
                        modifier = Modifier.fillMaxSize(),
                        model = bestSale.thumbnailImage,
                        contentScale = ContentScale.Crop,
                        contentDescription = null
                    )
                    IconButton(
                        modifier = Modifier
                            .zIndex(2f)
                            .align(Alignment.BottomEnd),
                        onClick = {
                            if (Constants.userToken.isNotEmpty()) {
                                isFavorite.value = !isFavorite.value
                                onFavoriteClick(bestSale.id, bestSale.isFavorite)
                                bestSale.isFavorite = !bestSale.isFavorite
                            } else {
                                onFavoriteClick(bestSale.id, bestSale.isFavorite)
                            }
                        },
                    ) {
                        Image(
                            modifier = Modifier.size(AppTheme.dimens.favoriteProductIconSize),
                            painter = if (isFavorite.value) {
                                painterResource(id = R.drawable.favorite_selected_icon)
                            } else {
                                painterResource(id = R.drawable.favorite_unselected_icon)
                            },
                            contentDescription = stringResource(id = R.string.favorite_not_selcted_icon)
                        )
                    }
                }

                DiscountBadge(
                    modifier = Modifier
                        .height(AppTheme.dimens.discountBadgeHeight)
                        .wrapContentWidth()
                        .offset(x = 8.dp, y = 8.dp)
                        .zIndex(3f),
                    label = bestSale.discount
                )
            }
        }
        Text(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 5.dp),
            text = bestSale.name,
            overflow = TextOverflow.Clip,
            maxLines = 2,
            minLines = 2,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.secondary,
                lineHeight = 20.sp
            ),
        )
        Text(
            modifier = Modifier
                .alpha(0.4f)
                .fillMaxSize()
                .padding(horizontal = 5.dp),
            text = bestSale.shopCategory.name,
            overflow = TextOverflow.Clip,
            maxLines = 1,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.secondary,
            ),
        )
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 5.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            StrokedPrice(price = bestSale.strokedPrice.removeZerosAfterComma())
            SpacerWidthSmall()
            MainPrice(
                price = bestSale.mainPrice,
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.onPrimary,
                ),
            )
        }
    }
}





