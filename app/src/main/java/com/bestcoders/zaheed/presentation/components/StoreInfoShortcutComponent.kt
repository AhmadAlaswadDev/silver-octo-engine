package com.bestcoders.zaheed.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.ui.theme.AppTheme

@Composable
fun StoreInfoShortcutComponent(
    modifier: Modifier = Modifier,
    logo: String,
    name: String,
    address: String,
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.dimens.paddingHorizontal),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.mediumLarge)
    ) {
        AsyncImage(
            modifier = Modifier
                .width(AppTheme.dimens.storeWithProductsStoreImageWidth)
                .height(AppTheme.dimens.storeWithProductsStoreImageHeight)
                .clip(RoundedCornerShape(Constants.CORNER_RADUIES)),
            contentScale = ContentScale.Inside,
            model = logo,
            contentDescription = null
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.small)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = name,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.secondary
                ),
                maxLines = 1,
                overflow = TextOverflow.Clip
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = address,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.secondary
                ),
                maxLines = 1,
                overflow = TextOverflow.Clip
            )
        }

    }

}

