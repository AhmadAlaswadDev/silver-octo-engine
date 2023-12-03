package com.bestcoders.zaheed.presentation.screens.home.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.ui.theme.AppTheme

@Composable
fun NearbyStoresHeader() {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(AppTheme.dimens.paddingHorizontal),
        text = stringResource(R.string.nearby_stores),
        style = MaterialTheme.typography.headlineMedium.copy(
            fontWeight = FontWeight.SemiBold
        ),
        textAlign = TextAlign.Start,
    )
}






