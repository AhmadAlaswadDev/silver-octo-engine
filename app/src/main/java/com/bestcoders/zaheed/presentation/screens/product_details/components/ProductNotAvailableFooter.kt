package com.bestcoders.zaheed.presentation.screens.product_details.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.ui.theme.AppTheme
import com.bestcoders.zaheed.ui.theme.CustomColor

@Composable
fun ProductNotAvailableFooter() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                clip = true
                shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp)
                shadowElevation = 50f
            }
            .height(AppTheme.dimens.navigationBarHeight)
            .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp)),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = CustomColor.White,
            disabledContainerColor = CustomColor.White,
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
            content = {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = AppTheme.dimens.paddingHorizontal),
                    text = stringResource(R.string.this_product_is_not_available),
                    overflow = TextOverflow.Clip,
                    maxLines = 1,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.primary,
                    ),
                )
            }
        )
    }
}