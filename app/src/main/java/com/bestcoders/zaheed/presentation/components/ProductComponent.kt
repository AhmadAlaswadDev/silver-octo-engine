package com.bestcoders.zaheed.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.bestcoders.zaheed.domain.model.products.Product
import com.bestcoders.zaheed.presentation.screens.home.components.DiscountBadge
import com.bestcoders.zaheed.ui.theme.AppTheme
import com.bestcoders.zaheed.ui.theme.CustomColor

@Composable
fun ProductComponent(
    modifier: Modifier = Modifier,
    product: Product,
    onFavoriteClick: ((Int, Boolean) -> Unit)? = null,
    onProductClick: (Int) -> Unit = {},
) {

    val isFavorite = remember {
        mutableStateOf(product.isFavorite)
    }

    Card(
        modifier = modifier
            .width(AppTheme.dimens.productItemWidth)
            .height(AppTheme.dimens.productItemHeight)
            .background(CustomColor.White)
            .clickable { onProductClick(product.id) }
            .shadow(
                elevation = 15.dp,
                shape = RoundedCornerShape(10.dp),
                ambientColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
            ),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = CustomColor.White,
            disabledContainerColor = CustomColor.White,
        )
    ) {
        Card(
            modifier = Modifier
                .width(AppTheme.dimens.productImageWidth)
                .height(AppTheme.dimens.productImageHeight),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = CustomColor.White,
                disabledContainerColor = CustomColor.White,
            )
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .zIndex(1f),
                    contentAlignment = Alignment.Center,
                ) {
                    AsyncImage(
                        modifier = Modifier.fillMaxSize(),
                        model = product.thumbnailImage,
                        contentScale = ContentScale.Crop,
                        contentDescription = null
                    )
                }
                if (onFavoriteClick != null) {
                    IconButton(
                        onClick = {
                            if (Constants.userToken.isNotEmpty()) {
                                isFavorite.value = !isFavorite.value!!
                                onFavoriteClick(product.id, product.isFavorite!!)
                                product.isFavorite = !product.isFavorite!!
                            } else {
                                onFavoriteClick(product.id, product.isFavorite!!)
                            }
                        },
                        modifier = Modifier
                            .zIndex(3f)
                            .align(Alignment.BottomEnd),
                    ) {
                        Image(
                            modifier = Modifier.size(AppTheme.dimens.favoriteProductIconSize),
                            painter = if (isFavorite.value!!) {
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
                        .offset(x = 8.dp, y = 8.dp)
                        .height(AppTheme.dimens.discountBadgeHeight)
                        .wrapContentWidth()
                        .zIndex(3f),
                    label = product.discount
                )
            }
        }
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp),
            text = product.name,
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
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 5.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            StrokedPrice(price = product.strokedPrice.removeZerosAfterComma())
            SpacerWidthSmall()
            MainPrice(
                price = product.mainPrice,
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.onPrimary,
                ),
            )
        }
    }
}

