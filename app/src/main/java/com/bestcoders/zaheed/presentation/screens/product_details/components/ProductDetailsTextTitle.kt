package com.bestcoders.zaheed.presentation.screens.product_details.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.bestcoders.zaheed.ui.theme.AppTheme

@Composable
fun ProductDetailsTextTitle(modifier:Modifier = Modifier,text:String){
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.dimens.paddingHorizontal),
        text = text,
        overflow = TextOverflow.Clip,
        maxLines = 1,
        style = MaterialTheme.typography.headlineMedium.copy(
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Start,
            color = MaterialTheme.colorScheme.secondary
        ),
    )
}