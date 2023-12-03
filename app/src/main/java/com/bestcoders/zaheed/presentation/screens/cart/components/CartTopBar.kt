package com.bestcoders.zaheed.presentation.screens.cart.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.presentation.components.LineDivider
import com.bestcoders.zaheed.ui.theme.AppTheme

@Composable
fun CartTopBar() {
    Column(
            modifier = Modifier
                    .fillMaxWidth()
                    .height(AppTheme.dimens.cartTopBarHeight)
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.Start
    ) {
        Text(
                modifier = Modifier.padding(horizontal = AppTheme.dimens.paddingHorizontal),
                text = stringResource(R.string.shopping_cart),
                style = MaterialTheme.typography.displaySmall.copy(
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold,
                ),
        )
        LineDivider()
    }
}