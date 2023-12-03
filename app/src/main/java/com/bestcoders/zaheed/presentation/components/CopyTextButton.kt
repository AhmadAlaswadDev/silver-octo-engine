package com.bestcoders.zaheed.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.ui.theme.AppTheme

@Composable
fun CopyTextButton(copyText: String) {
    val clipboardManager = LocalClipboardManager.current
    IconButton(
        onClick = {
            clipboardManager.setText(AnnotatedString(copyText))
        },
        modifier = Modifier.size(AppTheme.dimens.leadingIconSize)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.copy_icon), contentDescription = null,
            tint = MaterialTheme.colorScheme.onTertiary
        )
    }
}