package com.bestcoders.zaheed.presentation.screens.search.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.presentation.components.SpacerHeightLarge
import com.bestcoders.zaheed.ui.theme.AppTheme
import com.bestcoders.zaheed.ui.theme.CustomColor

@Composable
fun NoSearchResultFound(
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
            text = stringResource(R.string.no_results_found_for_your_search),
            style = MaterialTheme.typography.headlineSmall.copy(
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)
            ),
        )
        SpacerHeightLarge()
        Text(
            modifier = Modifier.wrapContentSize(),
            text = stringResource(R.string.there_is_no_search_results_matched_with_your_search),
            style = MaterialTheme.typography.bodyMedium.copy(
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f),
            ),
        )
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