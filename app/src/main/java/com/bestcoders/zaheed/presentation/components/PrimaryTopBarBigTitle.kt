package com.bestcoders.zaheed.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.extentions.mirror
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.ui.theme.AppTheme

@Composable
fun PrimaryTopBarBigTitle(
    modifier: Modifier = Modifier,
    title: String,
    backButtonColor: Color = MaterialTheme.colorScheme.primary,
    onBackClicked: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(AppTheme.dimens.checkoutTopBarHeight)
            .padding(
                horizontal = AppTheme.dimens.paddingHorizontal,
            ),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.Start,
        content = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                IconButton(
                    onClick = onBackClicked,
                    modifier = Modifier.size(AppTheme.dimens.topBarIconSize),
                    content = {
                        Icon(
                            modifier = Modifier
                                .fillMaxSize()
                                .mirror(),
                            imageVector = Icons.Rounded.ArrowBack,
                            tint = backButtonColor,
                            contentDescription = stringResource(R.string.arrow_back_icon_desc)
                        )
                    },
                )
                Text(
                    modifier = Modifier.wrapContentHeight(),
                    text = stringResource(R.string.back),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.SemiBold,
                        color = backButtonColor
                    ),
                    textAlign = TextAlign.Start,
                )
            }
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                )
            )
        },
    )
}