package com.bestcoders.zaheed.presentation.screens.sign_in

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.core.util.UiEvent
import com.bestcoders.zaheed.domain.model.auth.VerifyCodeType
import com.bestcoders.zaheed.presentation.components.PrimaryButton
import com.bestcoders.zaheed.presentation.components.PrimarySnackbar
import com.bestcoders.zaheed.presentation.components.PrimaryTextField
import com.bestcoders.zaheed.ui.theme.AppTheme
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun SignInScreen(
    state: SignInState,
    onEvent: (SignInEvent) -> Unit,
    navigateToHomeScreen: () -> Unit,
    navigateToVerifyScreenScreen: (phoneNumber: String, verifyCodeType: Int) -> Unit,
    resetState: (SignInState) -> Unit,
    uiEvent: SharedFlow<UiEvent>,
) {
    val scrollState = rememberScrollState()
    val snackbarHostState = remember { SnackbarHostState() }
    val dimens = AppTheme.dimens
    val countryCode by remember { mutableStateOf(Constants.COUNTRY_CODE) }
    var phoneNumber by remember { mutableStateOf("") }
    val onPhoneNumberValueChange: (String) -> Unit = { value ->
        if (value.isEmpty() || (value.isNotEmpty() && value[0] == '5')) {
            if (value.length <= Constants.PHONE_NUMBER_LENGTH) {
                phoneNumber = value
            }
        }
    }

    fun validateLoginForm(): Boolean {
        return phoneNumber.trim()
            .isNotEmpty() && phoneNumber.trim().length == Constants.PHONE_NUMBER_LENGTH
    }

    LaunchedEffect(state.success) {
        if (state.success) {
            navigateToVerifyScreenScreen(phoneNumber, VerifyCodeType.Login.value)
            resetState(SignInState())
            return@LaunchedEffect
        }
    }

    LaunchedEffect(state.error) {
       if (validateLoginForm() && !state.success && !state.error.isNullOrEmpty() && state.error == Constants.N_V_U) {
            navigateToVerifyScreenScreen(phoneNumber, VerifyCodeType.Signup.value)
            return@LaunchedEffect
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .imePadding()
                    .verticalScroll(scrollState)
                    .padding(horizontal = AppTheme.dimens.paddingHorizontal),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ConstraintLayout {
                    val (skipForNowText, loginButton, mobileNumberTextField, titleText) = createRefs()
                    Text(
                        modifier = Modifier
                            .constrainAs(titleText) {
                                top.linkTo(parent.top, margin = dimens.extraLarge)
                                bottom.linkTo(mobileNumberTextField.top, margin = dimens.large)
                                start.linkTo(parent.start)
                            },
                        text = stringResource(id = R.string.sign_in),
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        textAlign = TextAlign.Start,
                    )
                    var showError by remember { mutableStateOf(false) }
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                        PrimaryTextField(
                            modifier = Modifier.constrainAs(mobileNumberTextField) {
                                bottom.linkTo(loginButton.top, margin = dimens.large)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            },
                            imeAction = ImeAction.Done,
                            text = phoneNumber,
                            onValueChange = onPhoneNumberValueChange,
                            placeHolder = stringResource(R.string.mobile_number_place_holder),
                            singleLine = true,
                            enabled = !state.isLoading,
                            keyboardType = KeyboardType.Phone,
                            label = stringResource(id = R.string.mobile_number),
                            leadingIcon = {
                                Row(
                                    modifier = Modifier.padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Image(
                                        painter = painterResource(R.drawable.saudi_arabia),
                                        contentDescription = null,
                                        modifier = Modifier.size(AppTheme.dimens.leadingIconSize)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "($countryCode)",
                                        textAlign = TextAlign.Start,
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = MaterialTheme.colorScheme.primary,
                                            fontWeight = FontWeight.Normal
                                        ),
                                    )
                                }
                            },
                            showError = showError,
                            errorMessage = if (phoneNumber.trim().isEmpty()) {
                                stringResource(id = R.string.phone_empty_error_message)
                            } else if (phoneNumber.trim().length != Constants.PHONE_NUMBER_LENGTH) {
                                stringResource(id = R.string.invalid_phone_error_message)
                            } else {
                                null
                            }
                        )
                    }
                    PrimaryButton(
                        modifier = Modifier.constrainAs(loginButton) {
                            bottom.linkTo(skipForNowText.top, margin = dimens.extraLarge)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                        text = stringResource(R.string.continue_text),
                        enabled = !state.isLoading,
                        isLoading = state.isLoading,
                        onClick = {
                            showError = true
                            if (validateLoginForm()) {
                                onEvent(
                                    SignInEvent.OnSendVerificationCodeClicked(
                                        phoneNumber = countryCode + phoneNumber,
                                    )
                                )
                            }
                        },
                        isFormValidate = validateLoginForm(),
                        iconSize = AppTheme.dimens.topBarIconStoreDetailsSize,
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .constrainAs(skipForNowText) {
                                bottom.linkTo(
                                    parent.bottom,
                                    margin = dimens.megaLarge
                                )
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            },
                        contentAlignment = Alignment.Center,
                        content = {
                            Text(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .clickable { navigateToHomeScreen() },
                                text = stringResource(R.string.skip_for_now),
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onTertiary
                                ),
                                textAlign = TextAlign.Center,
                            )
                        }
                    )
                }
            }
        }
    )

    PrimarySnackbar(snackbarHostState)
}
