package com.bestcoders.zaheed.presentation.screens.favorite.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.presentation.components.SpacerHeightLarge
import com.bestcoders.zaheed.presentation.components.SpacerWidthMedium
import com.bestcoders.zaheed.ui.theme.AppTheme
import com.bestcoders.zaheed.ui.theme.CustomColor

@Composable
fun NoFavoriteItemsFoundComponent(
    favoriteType:String,
    onStartShoppingClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = AppTheme.dimens.paddingHorizontal),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            modifier = Modifier.wrapContentSize(),
            text = stringResource(R.string.you_don_t_have_favorite) + " " + favoriteType + " " + stringResource(R.string.yet),
            style = MaterialTheme.typography.headlineSmall.copy(
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            ),
        )
        SpacerHeightLarge()
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.wrapContentSize(),
                text = stringResource(R.string.add_them_to_the_list_with_help),
                style = MaterialTheme.typography.bodyMedium.copy(
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f),
                ),
            )
            SpacerWidthMedium()
            Icon(
                modifier = Modifier.size(AppTheme.dimens.large),
                painter = painterResource(id = R.drawable.favorite_unselected_icon),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f),
            )
        }
        SpacerHeightLarge()
        OutlinedButton(
            modifier = Modifier
                .height(AppTheme.dimens.buttonHeight)
                .width(AppTheme.dimens.startShoppingWidth),
            onClick = onStartShoppingClick,
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(containerColor = CustomColor.White),
            border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.onPrimary),
            content = {
                Text(
                    text = stringResource(R.string.start_shopping),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                )
            }
        )
    }

}