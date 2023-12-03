package com.bestcoders.zaheed.presentation.screens.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import com.bestcoders.zaheed.presentation.components.SpacerHeightMedium
import com.bestcoders.zaheed.ui.theme.AppTheme
import com.bestcoders.zaheed.ui.theme.CustomColor

@Composable
fun NotSupportedLocation(
        modifier: Modifier = Modifier,
        onClick: () -> Unit,
) {
    Column(
            modifier = modifier
                    .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
    ) {
        Text(
                modifier = Modifier.wrapContentSize(),
                text = stringResource(R.string.your_location_is_not_supported),
                style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center
                ),
                textAlign = TextAlign.Center
        )
        SpacerHeightMedium()
        Text(
                modifier = Modifier.wrapContentSize(),
                text = stringResource(R.string.no_nearby_shops_available_at_this_location),
                style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f),
                        textAlign = TextAlign.Center
                ),
                textAlign = TextAlign.Center
        )
        SpacerHeightLarge()
        OutlinedButton(
                modifier = Modifier
                        .height(AppTheme.dimens.buttonHeight)
                        .width(AppTheme.dimens.startShoppingWidth),
                onClick = onClick,
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(containerColor = CustomColor.White),
                border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.onPrimary),
                content = {
                    Text(
                            text = stringResource(R.string.select_location),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.onPrimary,
                            )
                    )
                }
        )
//        Image(
//            modifier = Modifier.size(AppTheme.dimens.locationErrorIconSize),
//            painter = painterResource(id = R.drawable.location_error_icon),
//            contentDescription = null
//        )
    }
}