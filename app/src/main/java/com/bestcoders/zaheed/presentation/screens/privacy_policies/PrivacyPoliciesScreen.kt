package com.bestcoders.zaheed.presentation.screens.privacy_policies

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.util.UiEvent
import com.bestcoders.zaheed.presentation.components.AppError
import com.bestcoders.zaheed.presentation.components.NavigationTopBar
import com.bestcoders.zaheed.presentation.components.PrimaryProgress
import com.bestcoders.zaheed.presentation.components.PrimarySnackbar
import com.bestcoders.zaheed.presentation.screens.profile_screens.about_us.AboutUsEvent
import com.bestcoders.zaheed.ui.theme.AppTheme
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest


@Composable
fun PrivacyPolicyScreen(
    state: PrivacyPoliciesState,
    onEvent: (PrivacyPoliciesEvent) -> Unit,
    navigateBack: () -> Unit,
    onReloadClicked: () -> Unit,
    resetState: (PrivacyPoliciesState?) -> Unit,
    uiEvent: SharedFlow<UiEvent>,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(true) {
        uiEvent.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message, actionLabel = event.action
                    )
                }
            }
        }
    }

    LaunchedEffect(state.error) {
        if (!state.success && !state.error.isNullOrEmpty()) {
            onEvent(PrivacyPoliciesEvent.ShowSnackBar(message = state.error.toString()))
            resetState(state.copy(error = null))
            return@LaunchedEffect
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (!state.isLoading) {
                NavigationTopBar(
                    modifier = Modifier,
                    label = state.privacyPolicies?.title.toString(),
                    onBackClicked = {
                        navigateBack()
                    }
                )
            }
        },
        content = { padding ->
            if (state.privacyPolicies != null && !state.isLoading) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(
                            start = AppTheme.dimens.paddingHorizontal,
                            end = AppTheme.dimens.paddingHorizontal,
                            top = padding.calculateTopPadding(),
                            bottom = padding.calculateBottomPadding()
                        )
                ) {
                    val htmlText =
                        HtmlCompat.fromHtml(state.privacyPolicies.content, 0)
                    AndroidView(
                        modifier = Modifier.fillMaxSize(),
                        factory = { MaterialTextView(it) },
                        update = { it.text = htmlText }
                    )
                }
            }else if (state.privacyPolicies == null && !state.isLoading) {
                AppError(
                    error = stringResource(id = R.string.un_known_error),
                    onReloadClicked = onReloadClicked
                )
            } else {
                PrimaryProgress()
            }
        }
    )
    PrimarySnackbar(snackbarHostState)
}

