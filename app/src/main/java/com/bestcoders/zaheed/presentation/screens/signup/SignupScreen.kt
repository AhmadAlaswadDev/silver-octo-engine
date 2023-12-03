package com.bestcoders.zaheed.presentation.screens.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.extentions.isValidEmail
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.core.util.UiEvent
import com.bestcoders.zaheed.domain.model.auth.SignupData
import com.bestcoders.zaheed.presentation.components.DatePickerUI
import com.bestcoders.zaheed.presentation.components.NavigationTopBar
import com.bestcoders.zaheed.presentation.components.PasswordTrailingIconComponent
import com.bestcoders.zaheed.presentation.components.PrimaryButton
import com.bestcoders.zaheed.presentation.components.PrimarySelector
import com.bestcoders.zaheed.presentation.components.PrimarySnackbar
import com.bestcoders.zaheed.presentation.components.PrimaryTextField
import com.bestcoders.zaheed.presentation.screens.signup.components.PasswordConditionItem
import com.bestcoders.zaheed.presentation.screens.signup.components.PrivacyPoliciesButton
import com.bestcoders.zaheed.ui.theme.AppTheme
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SignupScreen(
    state: SignupState,
    onEvent: (SignupEvent) -> Unit,
    navigateBack: () -> Unit,
    navigateToTermsConditionsAndPrivacyScreen: (String) -> Unit,
    resetState: (SignupState?) -> Unit,
    uiEvent: SharedFlow<UiEvent>,
    phoneNumber: String,
    lang: String = Constants.DEFAULT_LANGUAGE,
    registrationRequestId: Int,
    navigateToHomeScreen: () -> Unit,
) {

    // Fields Attributes
    val countryCode by  rememberSaveable { mutableStateOf(Constants.COUNTRY_CODE) }
    val scrollState = rememberScrollState()
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible = rememberSaveable { mutableStateOf(false) }
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var birthDate by rememberSaveable { mutableStateOf("") }
    val gender = rememberSaveable { mutableStateOf(0) }
    val showDialog = rememberSaveable { mutableStateOf(false) }
    val genderOptions = listOf(stringResource(id = R.string.male), stringResource(id = R.string.female))


    // Field Changes Methods
    val onPasswordValueChange: (String) -> Unit = { value -> password = value }
    val onNameValueChange: (String) -> Unit = { value -> name = value }
    val onEmailValueChange: (String) -> Unit = { value -> email = value }

    fun validateSignupForm(): Boolean {
        val nameValid = name.trim().isNotEmpty() && name.contains(" ")
        val emailValid = email.trim().isNotEmpty() && email.isValidEmail()
        val birthDateValid = birthDate.trim().isNotEmpty()
        val passwordValid =
            password.trim()
                .isNotEmpty() && password.length >= Constants.PASSWORD_LENGTH && password.any { it.isDigit() } && password.any { it.isUpperCase() }
        val phoneNumberValid = phoneNumber.trim()
            .isNotEmpty() && phoneNumber.trim().length == Constants.PHONE_NUMBER_LENGTH

        return nameValid && emailValid && birthDateValid && passwordValid && phoneNumberValid
    }

    val snackbarHostState = remember {
        SnackbarHostState()
    }
    var showError by remember { mutableStateOf(false) }

    val dimens = AppTheme.dimens


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
        if (state.success && state.user != null) {
            navigateToHomeScreen()
            resetState(null)
            return@LaunchedEffect
        }
    }

    LaunchedEffect(state.error) {
        if (!state.success && !state.error.isNullOrEmpty()) {
            onEvent(SignupEvent.ShowSnackBar(message = state.error))
            resetState(null)
            return@LaunchedEffect
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            NavigationTopBar(
                label = stringResource(id = R.string.sign_up),
                onBackClicked = navigateBack
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .imePadding()
                    .verticalScroll(scrollState)
                    .padding(
                        horizontal = AppTheme.dimens.paddingHorizontal,
                        vertical = padding.calculateTopPadding()
                    ),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    ConstraintLayout {
                        val (passwordConditions, signupButtonRef, selectGenderTextRef, privacyRef, birthDateRef, datePickerRef, mobileFieldRef, passwordFieldRef, genderRef, nameFieldRef, emailFieldRef) = createRefs()
                        PrimaryTextField(
                            modifier = Modifier.constrainAs(nameFieldRef) {
                                top.linkTo(parent.top, margin = dimens.medium)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            },
                            text = name,
                            onValueChange = onNameValueChange,
                            placeHolder = stringResource(R.string.enter_your_name),
                            singleLine = true,
                            enabled = !state.isLoading,
                            label = stringResource(R.string.name),
                            showError = showError,
                            errorMessage = if (name.trim().isEmpty()) {
                                stringResource(id = R.string.name_is_required)
                            } else if (name.trim().isNotEmpty() && name.contains(" ")){
                                stringResource(R.string.name_should_be_container_first_and_last_name_with_space)
                            }else {
                                null
                            }
                        )
                        PrimaryTextField(
                            modifier = Modifier.constrainAs(emailFieldRef) {
                                top.linkTo(nameFieldRef.bottom, margin = dimens.small)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            },
                            text = email,
                            keyboardType = KeyboardType.Email,
                            onValueChange = onEmailValueChange,
                            placeHolder = stringResource(R.string.enter_your_email),
                            singleLine = true,
                            enabled = !state.isLoading,
                            label = stringResource(R.string.email),
                            showError = showError,
                            errorMessage = if (email.trim().isEmpty()) {
                                stringResource(id = R.string.email_empty_error_message)
                            } else if (!email.isValidEmail()) {
                                stringResource(id = R.string.email_not_valid_error_message)
                            } else {
                                null
                            }
                        )
                        PrimaryTextField(
                            modifier = Modifier
                                .constrainAs(birthDateRef) {
                                    top.linkTo(emailFieldRef.bottom, margin = dimens.small)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                }
                                .clickable {
                                    showDialog.value = true
                                },
                            keyboardType = KeyboardType.Email,
                            onValueChange = {},
                            placeHolder = stringResource(R.string.yy_mm_dd),
                            singleLine = true,
                            text = birthDate,
                            enabled = false,
                            trailingIcon = {
                                Icon(
                                    modifier = Modifier.size(AppTheme.dimens.trailingIconSize),
                                    tint = MaterialTheme.colorScheme.onTertiary,
                                    painter = painterResource(id = R.drawable.calendar_icon),
                                    contentDescription = stringResource(R.string.calendar_icon)
                                )
                            },
                            label = stringResource(R.string.date_of_birth),
                            showError = showError,
                            errorMessage = if (birthDate.trim().isEmpty()) {
                                stringResource(id = R.string.birth_date_empty_error_message)
                            } else {
                                null
                            }
                        )
                        if (showDialog.value) {
                            DatePickerUI(
                                modifier = Modifier
                                    .constrainAs(datePickerRef) {
                                        top.linkTo(birthDateRef.bottom)
                                        bottom.linkTo(genderRef.top)
                                        start.linkTo(parent.start)
                                        end.linkTo(parent.end)
                                    },
                                showDatePicker = showDialog
                            ) { value ->
                                showDialog.value = false
                                birthDate = value
                            }
                        }
                        Text(
                            modifier = Modifier
                                .fillMaxSize()
                                .constrainAs(selectGenderTextRef) {
                                    top.linkTo(birthDateRef.bottom, margin = dimens.small)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                }
                                .padding(AppTheme.dimens.small),
                            text = stringResource(R.string.select_gender),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.SemiBold
                            ),
                            textAlign = TextAlign.Start,
                        )
                        PrimarySelector(
                            modifier = Modifier
                                .constrainAs(genderRef) {
                                    top.linkTo(
                                        selectGenderTextRef.bottom,
                                        margin = dimens.small
                                    )
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                },
                            selectedItem = gender,
                            items = genderOptions,
                        )
                        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                            PrimaryTextField(
                                modifier = Modifier
                                    .constrainAs(mobileFieldRef) {
                                        top.linkTo(genderRef.bottom, margin = dimens.small)
                                        start.linkTo(parent.start)
                                        end.linkTo(parent.end)
                                    }
                                    .clip(RoundedCornerShape(10.dp)),
                                text = phoneNumber,
                                onValueChange = {},
                                singleLine = true,
                                enabled = false,
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
                            )
                        }
                        PrimaryTextField(
                            modifier = Modifier.constrainAs(passwordFieldRef) {
                                top.linkTo(mobileFieldRef.bottom, margin = dimens.small)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            },
                            text = password,
                            onValueChange = onPasswordValueChange,
                            placeHolder = stringResource(R.string.password_place_holder),
                            singleLine = true,
                            enabled = !state.isLoading,
                            visualTransformation = if (passwordVisible.value) PasswordVisualTransformation() else VisualTransformation.None,
                            trailingIcon = {
                                PasswordTrailingIconComponent(passwordVisible)
                            },
                            keyboardType = KeyboardType.Password,
                            label = stringResource(id = R.string.password),
                            showError = showError,
                            errorMessage = if (password.trim().isEmpty()) {
                                stringResource(id = R.string.password_empty_error_message)
                            } else {
                                null
                            }
                        )
                        Column(
                            modifier = Modifier
                                .wrapContentHeight()
                                .fillMaxWidth()
                                .constrainAs(passwordConditions) {
                                    top.linkTo(passwordFieldRef.bottom, margin = dimens.small)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                    bottom.linkTo(privacyRef.top)
                                },
                        ) {
                            PasswordConditionItem(
                                label = stringResource(id = R.string.at_least_6_characters),
                                success = password.count() >= Constants.PASSWORD_LENGTH,
                            )
                            PasswordConditionItem(
                                label = stringResource(id = R.string.uppercase_characters),
                                success = password.any { it.isUpperCase() },
                            )
                            PasswordConditionItem(
                                label = stringResource(id = R.string.numbers),
                                success = password.any { it.isDigit() },
                            )
                        }
                        PrivacyPoliciesButton(
                            modifier = Modifier.constrainAs(privacyRef) {
                                top.linkTo(passwordConditions.bottom, margin = dimens.small)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                bottom.linkTo(signupButtonRef.top)
                            },
                            onTermsConditionsClick = {
                                navigateToTermsConditionsAndPrivacyScreen("0")
                            },
                            onPrivacyPoliciesClick = {
                                navigateToTermsConditionsAndPrivacyScreen("1")
                            }
                        )
                        PrimaryButton(
                            modifier = Modifier.constrainAs(signupButtonRef) {
                                top.linkTo(privacyRef.bottom, margin = dimens.large)
                                bottom.linkTo(parent.bottom, margin = dimens.large)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            },
                            text = stringResource(R.string.sign_up),
                            enabled = !state.isLoading,
                            isLoading = state.isLoading,
                            onClick = {
                                showError = true
                                if (validateSignupForm()) {
                                    onEvent(
                                        SignupEvent.OnSignupClicked(
                                            SignupData(
                                                birthDate = birthDate,
                                                gender = if (gender.value == 0) {
                                                    Constants.MALE
                                                } else {
                                                    Constants.FEMALE
                                                },
                                                name = name,
                                                password = password,
                                                passwordConfirmation = password,
                                                phoneNumber = countryCode + phoneNumber,
                                                lang = lang,
                                                email = email,
                                                registrationRequestId = registrationRequestId.toString()
                                            )
                                        )
                                    )
                                }
                            },
                            isFormValidate = validateSignupForm(),
                            iconSize = AppTheme.dimens.topBarIconStoreDetailsSize,
                        )
                    }
                }
            )
        }
    )
    PrimarySnackbar(snackbarHostState)
}

