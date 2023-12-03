package com.bestcoders.zaheed.presentation.screens.store_return_policy

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.presentation.components.AppError
import com.bestcoders.zaheed.presentation.components.PrimaryProgress
import com.bestcoders.zaheed.presentation.components.PrimarySnackbar
import com.bestcoders.zaheed.presentation.components.PrimaryTopBarBigTitle
import com.bestcoders.zaheed.ui.theme.AppTheme
import com.google.android.material.textview.MaterialTextView


@Composable
fun StoreReturnPolicyScreen(
    returnPolicy: String,
    navigateBack: () -> Unit,
) {

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            PrimaryTopBarBigTitle(
                title = stringResource(id = R.string.return_policy),
                backButtonColor = MaterialTheme.colorScheme.primary,
                onBackClicked = {
                    navigateBack()
                }
            )
        },
        content = { padding ->
            if (returnPolicy.isNotEmpty()) {
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
                    val htmlText = HtmlCompat.fromHtml(returnPolicy, 0)
                    AndroidView(
                        modifier = Modifier.fillMaxSize(),
                        factory = { MaterialTextView(it) },
                        update = { it.text = htmlText }
                    )
                }
            } else if (returnPolicy.isEmpty()) {
                AppError(
                    error = stringResource(id = R.string.un_known_error),
                    onReloadClicked = null
                )
            } else {
                PrimaryProgress()
            }

        }
    )

    PrimarySnackbar(snackbarHostState)
}

