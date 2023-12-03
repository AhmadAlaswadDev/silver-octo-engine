package com.bestcoders.zaheed.presentation.screens.category.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.extentions.mirror
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.ui.theme.AppTheme

@Composable
fun CategoryTopBar(
    modifier: Modifier = Modifier,
    label:String,
    onFilterClicked: () -> Unit,
    onBackClicked: () -> Unit
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(AppTheme.dimens.topBarHeight)
            .padding(
                vertical = AppTheme.dimens.paddingVertical,
                horizontal = AppTheme.dimens.paddingHorizontal
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        IconButton(
            onClick = onBackClicked,
            modifier = Modifier.size(AppTheme.dimens.topBarIconSize)
        ) {
            Icon(
                modifier = Modifier
                    .fillMaxSize()
                    .mirror(),
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = null
            )
        }
        Text(
            modifier = Modifier.wrapContentWidth(),
            text = label,
            style = MaterialTheme.typography.displaySmall.copy(
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.SemiBold,
            ),
            textAlign = TextAlign.Start,
        )
        IconButton(
            onClick = onFilterClicked,
            modifier = Modifier
                .size(AppTheme.dimens.topBarIconSize)
                .align(Alignment.CenterVertically)
        ) {
            Image(
                painter = painterResource(R.drawable.filter_icon),
                contentDescription = stringResource(R.string.filter_icon),
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}