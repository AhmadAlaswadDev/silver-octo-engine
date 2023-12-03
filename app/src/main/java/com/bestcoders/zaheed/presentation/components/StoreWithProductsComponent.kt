package com.bestcoders.zaheed.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.extentions.removePadding
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.domain.model.products.Store
import com.bestcoders.zaheed.ui.theme.AppTheme

@Composable
fun StoreWithProductsComponent(
    modifier: Modifier = Modifier,
    store: Store,
    onFavoriteClick: (Int, Boolean) -> Unit,
    onProductClick: (Int) -> Unit,
    onStoreClick: (Int) -> Unit
) {
    val lazyListState = rememberLazyListState()
    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current).data(data = store.logo)
            .apply(block = fun ImageRequest.Builder.() {
                crossfade(true)
            }).build()
    )
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        SpacerHeightMedium()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppTheme.dimens.paddingHorizontal),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                modifier = Modifier
                    .width(AppTheme.dimens.storeWithProductsStoreImageWidth)
                    .height(AppTheme.dimens.storeWithProductsStoreImageHeight)
                    .clip(RoundedCornerShape(Constants.CORNER_RADUIES.dp))
                    .clickable { onStoreClick(store.id) },
                contentScale = ContentScale.Inside,
                painter = painter,
                contentDescription = null
            )
            SpacerWidthMedium()
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    modifier = Modifier.clickable { onStoreClick(store.id) },
                    text = store.name,
                    overflow = TextOverflow.Clip,
                    maxLines = 1,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colorScheme.secondary
                    ),
                )
                if (store.category != null) {
                    Text(
                        modifier = Modifier.alpha(0.4f),
                        text = store.category.name,
                        overflow = TextOverflow.Clip,
                        maxLines = 1,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Start,
                            color = MaterialTheme.colorScheme.secondary,
                        ),
                    )
                }
            }
        }
        SpacerHeightMedium()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppTheme.dimens.paddingHorizontal),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                modifier = Modifier.size(AppTheme.dimens.nearbyLocationIcon),
                painter = painterResource(id = R.drawable.location_icon),
                contentDescription = stringResource(id = R.string.location_icon),
                tint = MaterialTheme.colorScheme.onTertiary
            )
            Text(
                text = store.address + " (" + store.distance + stringResource(id = R.string.km) + ")",
                maxLines = 1,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)
                ),
            )
        }
        SpacerHeightMedium()

        if (!store.products.isNullOrEmpty()) {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                state = lazyListState,
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Top
            ) {

                items(
                    count = store.products.size,
                    key = {
                        store.products[it].hashCode()
                    }
                ) { index ->
                    SpacerWidthMediumLarge()
                    ProductComponent(
                        modifier = Modifier,
                        product = store.products[index],
                        onFavoriteClick = onFavoriteClick,
                        onProductClick = onProductClick
                    )
                    if (index == store.products.size - 1) {
                        SpacerWidthMediumLarge()
                    }
                }
            }
            SpacerHeightMedium()
        }

        SpacerHeightMedium()
        LineDivider(Modifier.removePadding(-AppTheme.dimens.paddingHorizontal))
        SpacerHeightMedium()
    }
}

