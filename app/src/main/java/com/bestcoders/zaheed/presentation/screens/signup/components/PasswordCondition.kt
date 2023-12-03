package com.bestcoders.zaheed.presentation.screens.signup.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.presentation.components.SpacerWidthSmall
import com.bestcoders.zaheed.ui.theme.AppTheme

@Composable
fun PasswordConditionItem(success: Boolean, label: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(AppTheme.dimens.passwordConditionIconSize),
            tint = if (success) {
                MaterialTheme.colorScheme.onSecondary
            } else {
                MaterialTheme.colorScheme.onPrimary
            },
            painter = painterResource(id = R.drawable.check_icon),
            contentDescription = null
        )
        SpacerWidthSmall()
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.SemiBold,
                color = if (success) {
                    MaterialTheme.colorScheme.onSecondary
                } else {
                    MaterialTheme.colorScheme.onPrimary
                },
            ),
            textAlign = TextAlign.Start,
        )
    }
}