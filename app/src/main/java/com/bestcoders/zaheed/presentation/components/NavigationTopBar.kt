package com.bestcoders.zaheed.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.bestcoders.zaheed.core.extentions.mirror
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.ui.theme.AppTheme


@Composable
fun NavigationTopBar(modifier: Modifier = Modifier, label: String, onBackClicked: () -> Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.dimens.paddingHorizontal)
            .background(MaterialTheme.colorScheme.background)
            .height(AppTheme.dimens.topBarHeight),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        IconButton(
            onClick = onBackClicked,
            modifier = Modifier.fillMaxHeight()
        ) {
            Icon(
                modifier = Modifier
                    .size(AppTheme.dimens.topBarIconSize)
                    .mirror(),
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = null
            )
        }
        Box(
            modifier = Modifier
                .fillMaxHeight(0.4f)
                .fillMaxWidth(0.8f)
                .align(Alignment.CenterVertically),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center),
                text = label,
                style = MaterialTheme.typography.displaySmall.copy(
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                ),
                textAlign = TextAlign.Center,
            )
        }
    }
}