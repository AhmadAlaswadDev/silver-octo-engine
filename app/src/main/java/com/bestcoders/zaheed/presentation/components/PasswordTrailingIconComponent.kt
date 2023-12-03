package com.bestcoders.zaheed.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.ui.theme.AppTheme

@Composable
fun PasswordTrailingIconComponent(passwordVisible: MutableState<Boolean>) {
    val image =
        if (passwordVisible.value) {
            painterResource(id = R.drawable.hide_icon)
        } else {
            painterResource(id = R.drawable.show_icon)
        }
    val description = if (passwordVisible.value) {
        stringResource(R.string.hide_password)
    } else {
        stringResource(
            R.string.show_password
        )
    }
    IconButton(
        modifier = Modifier.size(AppTheme.dimens.large),
        onClick = { passwordVisible.value = !passwordVisible.value }) {
        Icon(painter = image, description)
    }
}