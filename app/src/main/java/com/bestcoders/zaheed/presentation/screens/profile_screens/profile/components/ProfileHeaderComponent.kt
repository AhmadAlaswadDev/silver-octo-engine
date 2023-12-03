package com.bestcoders.zaheed.presentation.screens.profile_screens.profile.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.presentation.components.YouSavedBordered

@Composable
fun ProfileHeaderComponent() {
    if (Constants.userToken.isNotEmpty()) {
        Text(
            text = "${stringResource(id = R.string.hello)} ${Constants.userName}",
            style = MaterialTheme.typography.headlineLarge.copy(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
            )
        )
        YouSavedBordered(saved = Constants.userSaved.toDouble())
    } else {
        Text(
            text = stringResource(id = R.string.profile),
            style = MaterialTheme.typography.headlineLarge.copy(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
            )
        )
    }
}