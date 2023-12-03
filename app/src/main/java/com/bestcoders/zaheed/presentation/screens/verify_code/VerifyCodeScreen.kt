package com.bestcoders.zaheed.presentation.screens.verify_code

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.constraintlayout.compose.ConstraintLayout
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.core.util.UiEvent
import com.bestcoders.zaheed.domain.model.auth.VerifyCodeType
import com.bestcoders.zaheed.presentation.components.NavigationTopBar
import com.bestcoders.zaheed.presentation.components.OtpTextField
import com.bestcoders.zaheed.presentation.components.PrimaryButton
import com.bestcoders.zaheed.presentation.components.PrimarySnackbar
import com.bestcoders.zaheed.presentation.components.SendCodeTimer
import com.bestcoders.zaheed.ui.theme.AppTheme
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun VerifyCodeScreen(
    state: VerifyCodeState,
    onEvent: (VerifyCodeEvent) -> Unit,
    navigateToHomeScreen: () -> Unit,
    navigateToSignInScreen: () -> Unit,
    navigateToSignupScreen: (phoneNumber: String, registrationRequestId: Int) -> Unit,
    navigateBack: () -> Unit,
    resetState: (VerifyCodeState?) -> Unit,
    uiEvent: SharedFlow<UiEvent>,
    timer: Int,
) {

    val countryCode by remember { mutableStateOf(Constants.COUNTRY_CODE) }
    var verificationCode by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val dimens = AppTheme.dimens
    val context = LocalContext.current



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

    LaunchedEffect(state.success) {
        if (state.success && state.user != null && state.registrationRequestId == null) {
            navigateToHomeScreen()
            resetState(null)
            return@LaunchedEffect
        } else if (state.success && state.registrationRequestId != null && state.user == null) {
            navigateToSignupScreen(state.phoneNumber!!, state.registrationRequestId)
            resetState(null)
            return@LaunchedEffect
        }
    }

    LaunchedEffect(state.verifyError) {
        if (!state.success && !state.verifyError.isNullOrEmpty()) {
            onEvent(VerifyCodeEvent.ShowSnackBar(message = state.verifyError))
            resetState(null)
            return@LaunchedEffect
        }
    }

    LaunchedEffect(state.reSendCodeSuccess) {
        if (state.reSendCodeSuccess) {
            onEvent(VerifyCodeEvent.ShowSnackBar(message = context.getString(R.string.code_sent_successfully)))
            return@LaunchedEffect
        }
    }

    LaunchedEffect(state.reSendCodeError) {
        if (!state.reSendCodeSuccess && !state.reSendCodeError.isNullOrEmpty()) {
            onEvent(VerifyCodeEvent.ShowSnackBar(message = state.reSendCodeError))
            resetState(null)
            return@LaunchedEffect
        }
    }

    LaunchedEffect(state.verifyDeleteUserSuccess) {
        if (state.verifyDeleteUserSuccess) {
            onEvent(VerifyCodeEvent.ShowSnackBar(message = context.getString(R.string.user_deleted_successfully)))
            navigateToSignInScreen()
            resetState(null)
            return@LaunchedEffect
        }
    }

    LaunchedEffect(state.verifyDeleteUserError) {
        if (!state.verifyDeleteUserSuccess && !state.verifyDeleteUserError.isNullOrEmpty()) {
            onEvent(VerifyCodeEvent.ShowSnackBar(message = state.verifyDeleteUserError))
            resetState(null)
            return@LaunchedEffect
        }
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            NavigationTopBar(
                label = stringResource(R.string.verification_code),
                onBackClicked = {
                    navigateBack()
                }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .imePadding()
                    .verticalScroll(rememberScrollState())
                    .padding(
                        horizontal = AppTheme.dimens.paddingHorizontal,
                        vertical = padding.calculateTopPadding()
                    ),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    ConstraintLayout {
                        val (infoTextRef, otpTextFieldRef, continueButtonRef, timerRef) = createRefs()
                        Text(
                            modifier = Modifier
                                .padding(horizontal = AppTheme.dimens.medium)
                                .constrainAs(infoTextRef) {
                                    top.linkTo(parent.top, margin = dimens.medium)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                },
                            text = stringResource(R.string.please_check_your_messages_for_a_six_digit_security_code_and_enter_it_below),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Center,
                            ),
                            textAlign = TextAlign.Center,
                        )
                        var showVerifyError by remember { mutableStateOf(false) }
                        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                            OtpTextField(
                                modifier = Modifier.constrainAs(otpTextFieldRef) {
                                    top.linkTo(infoTextRef.bottom, margin = dimens.medium)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                },
                                otpText = verificationCode,
                                onOtpTextChange = { value, _ ->
                                    verificationCode = value
                                },
                                showError = showVerifyError,
                                errorMessage = if (verificationCode.trim().isEmpty()) {
                                    stringResource(id = R.string.verification_code_require)
                                } else if (verificationCode.length != Constants.VERIFICATION_CODE_LENGTH) {
                                    stringResource(id = R.string.verification_code_should_be_6_digits)
                                } else if (verificationCode.isNotEmpty() && !state.verifyError.isNullOrEmpty()) {
                                    state.verifyError
                                } else {
                                    null
                                }
                            )
                        }
                        SendCodeTimer(
                            modifier = Modifier.constrainAs(timerRef) {
                                top.linkTo(otpTextFieldRef.bottom, margin = dimens.large)
                                start.linkTo(parent.start)
                            },
                            timer = timer,
                            onClick = {
                                if (timer == Constants.RESEND_OTP_TIME) {
                                    when (state.verifyCodeType) {
                                        VerifyCodeType.Login.value -> {
                                            onEvent(
                                                VerifyCodeEvent.OnReSendCodeLoginClicked(
                                                    phoneNumber = countryCode + state.phoneNumber,
                                                    context = context
                                                )
                                            )
                                        }

                                        VerifyCodeType.Signup.value -> {
                                            onEvent(
                                                VerifyCodeEvent.OnReSendCodeRegisterClicked(
                                                    phoneNumber = countryCode + state.phoneNumber,
                                                )
                                            )
                                        }
                                    }
                                }
                            }
                        )
                        PrimaryButton(
                            modifier = Modifier.constrainAs(continueButtonRef) {
                                top.linkTo(timerRef.bottom, margin = dimens.large)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            },
                            text = stringResource(R.string.continue_text),
                            enabled = !state.isLoading,
                            isLoading = state.isLoading,
                            onClick = {
                                showVerifyError = true
                                if (verificationCode.isNotEmpty() && verificationCode.length == Constants.VERIFICATION_CODE_LENGTH) {
                                    when (state.verifyCodeType) {
                                        VerifyCodeType.Login.value -> {
                                            onEvent(
                                                VerifyCodeEvent.VerifyCodeLogin(
                                                    verificationCode = verificationCode,
                                                    phoneNumber = countryCode + state.phoneNumber,
                                                    context = context,
                                                )
                                            )
                                        }

                                        VerifyCodeType.Signup.value -> {
                                            onEvent(
                                                VerifyCodeEvent.VerifyCodeRegister(
                                                    verificationCode = verificationCode,
                                                    phoneNumber = countryCode + state.phoneNumber,
                                                )
                                            )
                                        }

                                        VerifyCodeType.DeleteUser.value -> {
                                            onEvent(
                                                VerifyCodeEvent.VerifyCodeDeleteUser(
                                                    verificationCode = verificationCode,
                                                )
                                            )
                                        }
                                    }
                                }
                            },
                            isFormValidate = verificationCode.isNotEmpty(),
                            iconSize = AppTheme.dimens.topBarIconStoreDetailsSize,
                        )
                    }
                }
            )
        }
    )
    PrimarySnackbar(snackbarHostState)
}