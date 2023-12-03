package com.bestcoders.zaheed.presentation.components

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.ui.theme.AppTheme

@SuppressLint("MissingPermission")
@Composable
fun GpsScreen(onGpsEnabled: @Composable () -> Unit) {
    val context = LocalContext.current
    val locationManager =
        context.getSystemService(ComponentActivity.LOCATION_SERVICE) as LocationManager

    var gpsEnabled by remember { mutableStateOf(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) }

    val gpsListener = remember {
        object : LocationListener {
            override fun onLocationChanged(location: Location) {}
            override fun onProviderDisabled(provider: String) {
                gpsEnabled = false
            }

            override fun onProviderEnabled(provider: String) {
                gpsEnabled = true
            }

            override fun onStatusChanged(
                provider: String?,
                status: Int,
                extras: android.os.Bundle?
            ) {
            }
        }
    }

    DisposableEffect(Unit) {
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, gpsListener)
        onDispose {
            locationManager.removeUpdates(gpsListener)
        }
    }

    if (!gpsEnabled) {
        AlertDialog(
            onDismissRequest = { },
            title = {
                Text(
                    modifier = Modifier.padding(horizontal = AppTheme.dimens.medium),
                    text = stringResource(id = R.string.gps_required),
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
                    text = stringResource(id = R.string.please_enable_gps_to_use_this_app),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Start,
                    ),
                    textAlign = TextAlign.Start,
                )
            },
            confirmButton = {
                PrimaryButton(
                    text = stringResource(R.string.enable_gps),
                    onClick = {
                        context.startActivity(Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                    },
                    iconSize = AppTheme.dimens.topBarIconStoreDetailsSize
                )
            }
        )
    } else {
        onGpsEnabled()
    }

    BackHandler {
        gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }
}
