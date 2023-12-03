package com.bestcoders.zaheed.presentation.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
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
import com.bestcoders.zaheed.ui.theme.AppTheme
import com.bestcoders.zaheed.ui.theme.CustomColor

@Composable
fun DiscountBadge(modifier: Modifier = Modifier, label: String) {
    Row(
        modifier = modifier
            .background(
                MaterialTheme.colorScheme.primaryContainer,
                RoundedCornerShape(50.dp)
            )
            .padding(
                horizontal = AppTheme.dimens.small,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(R.string.minus_text) + "$label ",
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = CustomColor.White
            ),
        )
        Image(
            modifier = Modifier.height(AppTheme.dimens.mediumLarge),
            painter = painterResource(id = R.drawable.price_percent_icon),
            contentDescription = null
        )
    }
}