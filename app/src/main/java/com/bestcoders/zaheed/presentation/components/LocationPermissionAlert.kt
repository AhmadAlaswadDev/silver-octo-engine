package com.bestcoders.zaheed.presentation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.ui.theme.AppTheme
import com.bestcoders.zaheed.ui.theme.ZaheedTheme
import com.bestcoders.zaheed.ui.theme.rememberWindowSizeClass

@Composable
fun LocationPermissionAlert(
    permissionGranted: MutableState<Boolean>,
    onPermissionGranted: @Composable () -> Unit,
    requestLocationPermission: () -> Unit,
) {

    val window = rememberWindowSizeClass()
    ZaheedTheme(window) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.surface
        ) {
            if (!permissionGranted.value) {
                AlertDialog(
                    onDismissRequest = {},
                    title = {
                        Text(
                            modifier = Modifier.padding(horizontal = AppTheme.dimens.medium),
                            text = stringResource(id = R.string.location_permission_required),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Start,
                            ),
                            textAlign = TextAlign.Start,
                        )
                    },
                    text = {
                        Text(
                            modifier = Modifier.padding(horizontal = AppTheme.dimens.medium),
                            text = stringResource(id = R.string.please_grant_location_permission_to_use_this_app),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Normal,
                                textAlign = TextAlign.Start,
                            ),
                            textAlign = TextAlign.Start,
                        )
                    },
                    confirmButton = {
                        PrimaryButton(
                            text = stringResource(id = R.string.grant_permission),
                            onClick = { requestLocationPermission() },
                            iconSize = AppTheme.dimens.topBarIconStoreDetailsSize
                        )
                    }
                )
            } else {
                onPermissionGranted()
            }
        }
    }
}