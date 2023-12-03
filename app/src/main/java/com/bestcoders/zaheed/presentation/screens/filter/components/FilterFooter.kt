package com.bestcoders.zaheed.presentation.screens.filter.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.presentation.components.PrimaryButton
import com.bestcoders.zaheed.presentation.components.SpacerWidthMedium
import com.bestcoders.zaheed.ui.theme.AppTheme
import com.bestcoders.zaheed.ui.theme.CustomColor

@Composable
fun FilterFooter(
    onResetClick: () -> Unit,
    onApplyClick: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        PrimaryButton(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            text = stringResource(R.string.reset),
            onClick = onResetClick,
            color = Color.Transparent,
            radius = 100f,
            borderColor = MaterialTheme.colorScheme.onPrimary,
            borderStroke = 1f,
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold,
            ),
            iconSize = AppTheme.dimens.topBarIconStoreDetailsSize,
        )
        SpacerWidthMedium()
        PrimaryButton(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            text = stringResource(id = R.string.apply),
            onClick = onApplyClick,
            radius = 100f,
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                color = CustomColor.White,
                fontWeight = FontWeight.Bold,
            ),
            iconSize = AppTheme.dimens.topBarIconStoreDetailsSize
        )
    }
}