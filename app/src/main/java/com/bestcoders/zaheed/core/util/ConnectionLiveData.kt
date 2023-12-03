package com.bestcoders.zaheed.core.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.bestcoders.zaheed.R

val LocalConnectionState =
    staticCompositionLocalOf<Boolean> { error("No LocalConnectionState provided") }

@Composable
fun NetworkConnectionAwareContent(content: @Composable () -> Unit) {
    var connectionState by remember { mutableStateOf(true) }

    val connectivityManager =
        LocalContext.current.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    DisposableEffect(Unit) {
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                connectionState = true
            }

            override fun onLost(network: Network) {
                connectionState = false
            }
        }

        val request = NetworkRequest.Builder().build()
        connectivityManager.registerNetworkCallback(request, networkCallback)

        onDispose {
            connectivityManager.unregisterNetworkCallback(networkCallback)
        }
    }

    CompositionLocalProvider(LocalConnectionState provides connectionState) {
        content()
    }
}

@Composable
fun NoInternetDialog() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    Text(
                        text = stringResource(id = R.string.no_internet_connection),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.SemiBold,
                        ),
                    )
                    Text(
                        text = stringResource(R.string.please_check_that_you_have_stable_internet_connection),
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f),
                            fontWeight = FontWeight.SemiBold,
                        ),
                    )
                }
            )
        }
    )
}
