package com.bestcoders.zaheed.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.bestcoders.zaheed.R

@Composable
fun NoDataFoundText() {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center,
        content = {
            Text(
                text = stringResource(R.string.no_data_found),
                style = MaterialTheme.typography.headlineLarge.copy(
                    color = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
    )
}
