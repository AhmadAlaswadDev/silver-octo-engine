package com.bestcoders.zaheed.presentation.screens.product_details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.domain.model.products.ProductDetails
import com.bestcoders.zaheed.domain.model.products.ProductVariationDetails
import com.bestcoders.zaheed.presentation.screens.home.components.DiscountBadge
import com.bestcoders.zaheed.ui.theme.AppTheme
import com.bestcoders.zaheed.ui.theme.CustomColor

@Composable
fun ProductDetailsHeader(
    onBackClicked: () -> Unit,
    onFavoriteClick: () -> Unit,
    product: ProductDetails,
    productVariationDetails: ProductVariationDetails? = null
) {
    val slides by remember {
        derivedStateOf { product.slides.offer }
    }

    val isFavorite = rememberSaveable {
        mutableStateOf(product.isFavorite)
    }

    Box(modifier = Modifier
        .fillMaxWidth()
        .height(AppTheme.dimens.headerBoxProductDetailsHeight),
        content = {
            ProductDetailsBanner { slides }
            Box(
                modifier = Modifier
                    .zIndex(2f)
                    .padding(AppTheme.dimens.paddingHorizontal)
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .size(AppTheme.dimens.topBarBoxIconProductDetailsSize)
                        .clickable { onBackClicked() }
                        .align(Alignment.TopStart)
                        .background(
                            CustomColor.White,
                            shape = RoundedCornerShape(Constants.CORNER_RADUIES.dp)
                        ),
                    contentAlignment = Alignment.Center,
                    content = {
                        Icon(
                            modifier = Modifier
                                .size(AppTheme.dimens.topBarIconProductDetailsSize)
                                .graphicsLayer(
                                    rotationY = when (Constants.DEFAULT_LANGUAGE) {
                                        Constants.SAUDI_LANGUAGE_CODE -> 180f
                                        else -> 0f
                                    }
                                ),
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = null
                        )
                    }
                )
                Box(
                    modifier = Modifier
                        .size(AppTheme.dimens.topBarBoxIconProductDetailsSize)
                        .clickable {
                            isFavorite.value = !isFavorite.value
                            onFavoriteClick()
                            product.isFavorite = !product.isFavorite
                        }
                        .align(Alignment.TopEnd)
                        .background(
                            CustomColor.White,
                            shape = RoundedCornerShape(Constants.CORNER_RADUIES.dp)
                        ),
                    contentAlignment = Alignment.Center,
                    content = {
                        Icon(
                            modifier = Modifier.size(AppTheme.dimens.topBarIconProductDetailsSize),
                            painter = if (isFavorite.value) {
                                painterResource(id = R.drawable.favorite_unselected_icon)
                            } else {
                                painterResource(id = R.drawable.border_favorite_icon)
                            },
                            tint = if (isFavorite.value) {
                                MaterialTheme.colorScheme.onPrimary
                            } else {
                                MaterialTheme.colorScheme.secondaryContainer.copy(
                                    alpha = 0.8f
                                )
                            },
                            contentDescription = stringResource(id = R.string.favorite_not_selcted_icon)
                        )
                    }
                )
                DiscountBadge(
                    modifier = Modifier
                        .height(AppTheme.dimens.discountBadgeHeightProductDetails)
                        .wrapContentSize()
                        .align(Alignment.BottomStart),
                    label = productVariationDetails?.discount?.toString() ?: product.discount.toString(),
                )
            }
        }
    )
}