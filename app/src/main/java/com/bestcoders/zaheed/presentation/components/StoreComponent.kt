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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.domain.model.products.Store
import com.bestcoders.zaheed.ui.theme.AppTheme

@Composable
fun StoreComponent(
    modifier:Modifier = Modifier,
    store: Store,
    onFavoriteClick: (Int, Boolean) -> Unit,
    onStoreClick: (Int) -> Unit
) {
    val isFavorite = remember {
        mutableStateOf(store.isFavorite)
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { onStoreClick(store.id) },
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
    ) {
        SpacerHeightMedium()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppTheme.dimens.paddingHorizontal),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                modifier = Modifier
                    .weight(1f)
                    .width(60.dp)
                    .height(70.dp)
                    .clip(RoundedCornerShape(Constants.CORNER_RADUIES)),
                contentScale = ContentScale.Inside,
                model = store.logo,
                contentDescription = null
            )
            SpacerWidthMedium()
            Column(
                modifier = Modifier
                    .weight(3f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = store.name,
                    overflow = TextOverflow.Clip,
                    maxLines = 1,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colorScheme.secondary
                    ),
                )
                Text(
                    modifier = Modifier.alpha(0.4f),
                    text = store.category?.name.toString(),
                    overflow = TextOverflow.Clip,
                    maxLines = 1,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colorScheme.secondary,
                    ),
                )
            }
            IconButton(
                onClick = {
                    isFavorite.value = !isFavorite.value
                    onFavoriteClick(store.id, store.isFavorite)
                    store.isFavorite = !store.isFavorite
                },
                modifier = Modifier.weight(1f),
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
                contentDescription = stringResource(
                    id = R.string.location_icon
                ),
                tint = MaterialTheme.colorScheme.onTertiary
            )
            SpacerWidthSmall()
            Text(
                text = store.distance.toString() + stringResource(R.string.km),
                maxLines = 1,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.secondary
                ),
            )
        }
        SpacerHeightMedium()
        LineDivider()
    }
}