package com.bestcoders.zaheed.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.extentions.color
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.ui.theme.AppTheme

@Composable
fun PrimarySnackbar(snackbarHostState: SnackbarHostState) {
    SnackbarHost(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.dimens.paddingHorizontal),
        hostState = snackbarHostState,
    ) { data ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = "#FFF2F2".color,
                    shape = RoundedCornerShape(Constants.CORNER_RADUIES)
                )
                .padding(
                    horizontal = AppTheme.dimens.paddingHorizontal,
                    vertical = AppTheme.dimens.medium
                )
                .clip(shape = RoundedCornerShape(Constants.CORNER_RADUIES.dp)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            content = {
                Icon(
                    modifier = Modifier.size(AppTheme.dimens.large),
                    painter = painterResource(id = R.drawable.failed_snackbar_icon),
                    contentDescription = null,
                    tint = "#FF5454".color,
                )
                SpacerWidthMedium()
                Text(
                    text = data.visuals.message,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        )
    }
}