package com.bestcoders.zaheed.presentation.screens.profile_screens.edit_profile

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.extentions.isValidEmail
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.core.util.UiEvent
import com.bestcoders.zaheed.domain.model.auth.VerifyCodeType
import com.bestcoders.zaheed.presentation.components.DatePickerUI
import com.bestcoders.zaheed.presentation.components.PrimaryButton
import com.bestcoders.zaheed.presentation.components.PrimaryProgress
import com.bestcoders.zaheed.presentation.components.PrimarySelector
import com.bestcoders.zaheed.presentation.components.PrimarySnackbar
import com.bestcoders.zaheed.presentation.components.PrimaryTextField
import com.bestcoders.zaheed.presentation.components.SpacerHeightLarge
import com.bestcoders.zaheed.presentation.components.SpacerHeightMedium
import com.bestcoders.zaheed.presentation.screens.profile_screens.edit_profile.components.EditProfileTopBar
import com.bestcoders.zaheed.presentation.screens.profile_screens.profile.ProfileEvent
import com.bestcoders.zaheed.ui.theme.AppTheme
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun EditProfileScreen(
    state: MutableState<EditProfileState>,
    onEvent: (EditProfileEvent) -> Unit,
    uiEvent: SharedFlow<UiEvent>,
    resetState: (EditProfileState) -> Unit,
    navigateBack: () -> Unit,
    navigateToVerifyCodeScreen: (verifyCodeType: Int) -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
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

    LaunchedEffect(state.value.success) {
        if (state.value.success) {
            onEvent(EditProfileEvent.ShowSnackBar(message = context.getString(R.string.profile_updated_successfully)))
            navigateBack()
            resetState(EditProfileState())
            return@LaunchedEffect
        }
    }

    LaunchedEffect(state.value.error) {
        if (!state.value.success && !state.value.error.isNullOrEmpty()) {
            onEvent(EditProfileEvent.ShowSnackBar(message = state.value.error.toString()))
            resetState(state.value.copy(error = null))
            return@LaunchedEffect
        }
    }

    LaunchedEffect(state.value.deleteUserSuccess) {
        if (state.value.deleteUserSuccess) {
            navigateToVerifyCodeScreen(VerifyCodeType.DeleteUser.value)
            resetState(state.value.copy(deleteUserSuccess = false))
            return@LaunchedEffect
        }
    }

    LaunchedEffect(state.value.deleteUserError) {
        if (!state.value.deleteUserSuccess && !state.value.deleteUserError.isNullOrEmpty()) {
            onEvent(EditProfileEvent.ShowSnackBar(message = state.value.deleteUserError.toString()))
            resetState(state.value.copy(deleteUserError = null))
            return@LaunchedEffect
        }
    }

    val countryCode by rememberSaveable { mutableStateOf(Constants.COUNTRY_CODE) }
    var phoneNumber by remember { mutableStateOf(Constants.userPhone.substring(4)) }
    var firstName by rememberSaveable { mutableStateOf(Constants.userName.split(" ")[0]) }
    var lastName by rememberSaveable { mutableStateOf(Constants.userName.split(" ")[1]) }
    var email by rememberSaveable { mutableStateOf(Constants.userEmail) }
    var birthDate by rememberSaveable { mutableStateOf(Constants.userBirthDate) }
    val gender = rememberSaveable {
        mutableStateOf(
            if (Constants.MALE == Constants.userGender) {
                0
            } else {
                1
            }
        )
    }
    val genderOptions =
        listOf(stringResource(id = R.string.male), stringResource(id = R.string.female))
    val showDialog = rememberSaveable { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }

    // Field Changes Methods
    val onFirstNameValueChange: (String) -> Unit = { value -> firstName = value }
    val onLastNameValueChange: (String) -> Unit = { value -> lastName = value }
    val onEmailValueChange: (String) -> Unit = { value -> email = value }
    val onPhoneNumberValueChange: (String) -> Unit = { value ->
        if (value.isEmpty() || (value.isNotEmpty() && value[0] == '5')) {
            if (value.length <= Constants.PHONE_NUMBER_LENGTH) {
                phoneNumber = value
            }
        }
    }

    fun isValidateForm(): Boolean {
        val firstNameValid = firstName.trim().isNotEmpty()
        val lastNameValid = lastName.trim().isNotEmpty()
        val emailValid = email.trim().isNotEmpty() && email.isValidEmail()
        val birthDateValid = birthDate.trim().isNotEmpty()
        return firstNameValid && lastNameValid && emailValid && birthDateValid
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            EditProfileTopBar(
                label = stringResource(R.string.profile_details),
                onBackClicked = navigateBack,
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(
                        start = AppTheme.dimens.paddingHorizontal,
                        end = AppTheme.dimens.paddingHorizontal,
                        top = padding.calculateTopPadding(),
                        bottom = padding.calculateBottomPadding(),
                    ),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    PrimaryTextField(
                        text = firstName,
                        label = stringResource(R.string.first_name),
                        placeHolder = stringResource(R.string.enter_you_first_name),
                        onValueChange = onFirstNameValueChange,
                        showError = showError,
                        errorMessage = if (firstName.trim().isEmpty()) {
                            stringResource(R.string.first_name_required)
                        } else {
                            null
                        }
                    )
                    PrimaryTextField(
                        text = lastName,
                        label = stringResource(R.string.last_name),
                        placeHolder = stringResource(R.string.enter_you_last_name),
                        onValueChange = onLastNameValueChange,
                        showError = showError,
                        errorMessage = if (lastName.trim().isEmpty()) {
                            stringResource(R.string.last_name_required)
                        } else {
                            null
                        }
                    )
                    PrimaryTextField(
                        text = email,
                        keyboardType = KeyboardType.Email,
                        onValueChange = onEmailValueChange,
                        placeHolder = stringResource(R.string.enter_your_email),
                        enabled = !state.value.isLoading,
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
                        imeAction = ImeAction.Done,
                        text = phoneNumber,
                        onValueChange = onPhoneNumberValueChange,
                        placeHolder = stringResource(R.string.mobile_number_place_holder),
                        enabled = !state.value.isLoading,
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
                    PrimaryTextField(
                        modifier = Modifier
                            .clickable {
                                showDialog.value = true
                            },
                        onValueChange = {},
                        placeHolder = stringResource(R.string.yy_mm_dd),
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
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(AppTheme.dimens.small),
                        text = stringResource(R.string.select_gender),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        textAlign = TextAlign.Start,
                    )
                    PrimarySelector(
                        modifier = Modifier,
                        selectedItem = gender,
                        items = genderOptions,
                    )
                    SpacerHeightLarge()
                    PrimaryButton(
                        text = stringResource(R.string.save),
                        enabled = !state.value.isLoading,
                        isLoading = state.value.isLoading,
                        onClick = {
                            showError = true
                            if (isValidateForm()) {
                                onEvent(
                                    EditProfileEvent.OnEditClicked(
                                        name = "$firstName $lastName",
                                        birthDate = birthDate,
                                        email = email,
                                        gender = if (gender.value == 0) {
                                            Constants.MALE
                                        } else {
                                            Constants.FEMALE
                                        },
                                        phoneNumber = countryCode + phoneNumber,
                                    )
                                )
                            }
                        },
                        isFormValidate = isValidateForm(),
                        iconSize = AppTheme.dimens.topBarIconStoreDetailsSize,
                    )
                    SpacerHeightMedium()
                    TextButton(
                        modifier = Modifier.wrapContentSize(),
                        onClick = { onEvent(EditProfileEvent.OnDeleteUserClicked) },
                        content = {
                            Text(
                                text = stringResource(id = R.string.delete_account),
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    textAlign = TextAlign.Center,
                                )
                            )
                        },
                    )
                },
            )
        },
    )


    if (showDialog.value) {
        DatePickerUI(
            modifier = Modifier,
            showDatePicker = showDialog,
        ) { value ->
            showDialog.value = false
            birthDate = value
        }
    }
    PrimarySnackbar(snackbarHostState)
    if (state.value.isLoading) {
        PrimaryProgress()
    }

}