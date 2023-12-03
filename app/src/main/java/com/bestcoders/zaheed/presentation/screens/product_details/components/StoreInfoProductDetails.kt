package com.bestcoders.zaheed.presentation.screens.product_details.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.domain.model.products.StoreProductDetails
import com.bestcoders.zaheed.ui.theme.AppTheme

@Composable
fun StoreInfoProductDetails(
    store: StoreProductDetails,
    onStoreClick: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.dimens.paddingHorizontal),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start
    ) {
        AsyncImage(
            modifier = Modifier
                .weight(1f)
                .width(AppTheme.dimens.storeWithProductsStoreImageWidth)
                .height(AppTheme.dimens.storeWithProductsStoreImageHeight)
                .clip(RoundedCornerShape(Constants.CORNER_RADUIES))
                .clickable { onStoreClick(store.id) },
            contentScale = ContentScale.Inside,
            model = store.logo,
            contentDescription = null
        )
        Column(
            modifier = Modifier
                .weight(3f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            ProductDetailsTextTitle(
                text = store.name,
                modifier = Modifier.clickable { onStoreClick(store.id) },
            )
            ProductDetailsTextSubtitle(
                text = store.address + " (" + store.distance + stringResource(
                    id = R.string.km
                ) + ")"
            )
        }
    }
}