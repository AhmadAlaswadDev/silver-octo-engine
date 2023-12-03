package com.bestcoders.zaheed.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.util.Constants

@Composable
fun SendCodeTimer(timer: Int, modifier: Modifier, onClick: () -> Unit) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = modifier
                .wrapContentSize()
                .clickable {
                    onClick()
                },
            text =
            stringResource(R.string.resend_code_after),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = if (timer == Constants.RESEND_OTP_TIME) {
                    MaterialTheme.colorScheme.onTertiary
                } else {
                    MaterialTheme.colorScheme.secondary
                },
                textAlign = TextAlign.Start,
                textDecoration = TextDecoration.Underline,
            ),
            textAlign = TextAlign.Start,
        )
        if (timer != Constants.RESEND_OTP_TIME) {
            Text(
                modifier = Modifier
                    .wrapContentSize(),
                text =
                " "+"(${
                    (timer / 60).toString().padStart(2, '0')
                }:${
                    (timer % 60).toString().padStart(2, '0')
                })",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onTertiary,
                    textAlign = TextAlign.Start,
                    textDecoration = TextDecoration.Underline,
                ),
                textAlign = TextAlign.Start,
            )
        }
    }
}