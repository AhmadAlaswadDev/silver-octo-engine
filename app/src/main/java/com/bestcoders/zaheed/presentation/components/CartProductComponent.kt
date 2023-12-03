package com.bestcoders.zaheed.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.domain.model.products.Product
import com.bestcoders.zaheed.presentation.screens.home.components.DiscountBadge
import com.bestcoders.zaheed.ui.theme.AppTheme

@Composable
fun CartProductComponent(
    modifier: Modifier = Modifier,
    product: Product,
    onProductClick: (Int) -> Unit,
) {

    Card(
        modifier = modifier
            .wrapContentSize()
            .background(Color.Transparent)
            .clickable {
                onProductClick(product.id)
            },
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
        ),
    ) {
        Card(
            modifier = Modifier
                .size(AppTheme.dimens.productCartImageSize),
            shape = RoundedCornerShape(Constants.CORNER_RADUIES.dp),
            border = BorderStroke(
                2.dp,
                MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f)
            ),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.1f),
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.1f),
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
                        contentDescription = stringResource(R.string.category_image)
                    )
                }
                DiscountBadge(
                    modifier = Modifier
                        .offset(x = 8.dp, y = 8.dp)
                        .height(AppTheme.dimens.discountBadgeHeight)
                        .wrapContentWidth()
                        .zIndex(3f),
                    label = product.discount.toString()
                )
            }
        }
    }
}

