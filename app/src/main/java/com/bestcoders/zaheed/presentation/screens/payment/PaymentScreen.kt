package com.bestcoders.zaheed.presentation.screens.payment

import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.presentation.components.NavigationTopBar
import com.bestcoders.zaheed.presentation.components.PrimarySnackbar
import com.bestcoders.zaheed.ui.theme.AppTheme

@Composable
fun PaymentScreen(
    paymentMethodUrl: String,
    navigateToPaymentSuccess: () -> Unit,
    navigateBack: () -> Unit,
) {

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            NavigationTopBar(
                label = stringResource(id = R.string.payment),
                onBackClicked = navigateBack
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = AppTheme.dimens.paddingHorizontal,
                        end = AppTheme.dimens.paddingHorizontal,
                        top = padding.calculateTopPadding(),
                        bottom = padding.calculateBottomPadding()
                    ),
                content = {
                    PaymentWebViewComposable(
                        url = paymentMethodUrl,
                        onNavigationComplete = { isSuccess ->
                            if (isSuccess) {
                                // Navigation was successful
                                navigateToPaymentSuccess()
                            } else {
                                // Navigation failed
//                                navigateBack()
                            }
                        },
                    )
                }
            )
        }
    )
    PrimarySnackbar(snackbarHostState)
}


@Composable
fun PaymentWebViewComposable(url: String, onNavigationComplete: (Boolean) -> Unit) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                webViewClient = object : WebViewClient() {
                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)

                        val isSuccess = url?.contains(
                            "https://dev.zaheed.sa/api/v2/telr/success",
                            ignoreCase = true
                        ) == true
                        onNavigationComplete(isSuccess)
                    }

                    override fun onReceivedHttpError(
                        view: WebView?,
                        request: WebResourceRequest?,
                        errorResponse: WebResourceResponse?
                    ) {
                        super.onReceivedHttpError(view, request, errorResponse)
                        onNavigationComplete(false)
                    }


                    override fun onReceivedError(
                        view: WebView?,
                        request: WebResourceRequest?,
                        error: WebResourceError?
                    ) {
                        super.onReceivedError(view, request, error)

                        onNavigationComplete(false)
                    }
                }
                loadUrl(url)
            }
        }
    )
}
