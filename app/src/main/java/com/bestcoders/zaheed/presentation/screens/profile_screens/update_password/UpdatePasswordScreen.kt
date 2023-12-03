package com.bestcoders.zaheed.presentation.screens.profile_screens.update_password

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.core.util.UiEvent
import com.bestcoders.zaheed.presentation.components.NavigationTopBar
import com.bestcoders.zaheed.presentation.components.PasswordTrailingIconComponent
import com.bestcoders.zaheed.presentation.components.PrimaryButton
import com.bestcoders.zaheed.presentation.components.PrimarySnackbar
import com.bestcoders.zaheed.presentation.components.PrimaryTextField
import com.bestcoders.zaheed.presentation.screens.signup.components.PasswordConditionItem
import com.bestcoders.zaheed.ui.theme.AppTheme
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun UpdatePasswordScreen(
    state: MutableState<UpdatePasswordState>,
    onEvent: (UpdatePasswordEvent) -> Unit,
    uiEvent: SharedFlow<UiEvent>,
    navigateBack: () -> Unit,
    resetState: (UpdatePasswordState) -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    var oldPassword by rememberSaveable { mutableStateOf("") }
    var newPassword by rememberSaveable { mutableStateOf("") }
    val oldPasswordVisible = rememberSaveable { mutableStateOf(false) }
    val newPasswordVisible = rememberSaveable { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }

    val onOldPasswordValueChange: (String) -> Unit = { value -> oldPassword = value }
    val onNewPasswordValueChange: (String) -> Unit = { value -> newPassword = value }

    fun isValidateForm(): Boolean {
        return newPassword.trim()
            .isNotEmpty() && newPassword.length >= Constants.PASSWORD_LENGTH && newPassword.any { it.isDigit() } && newPassword.any { it.isUpperCase() }
    }

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
            onEvent(UpdatePasswordEvent.ShowSnackBar(message = context.getString(R.string.password_updated_successfully)))
            resetState(UpdatePasswordState())
            navigateBack()
            return@LaunchedEffect
        }
    }

    LaunchedEffect(state.value.error) {
        if (!state.value.success && !state.value.error.isNullOrEmpty()) {
            onEvent(UpdatePasswordEvent.ShowSnackBar(message = state.value.error.toString()))
            resetState(state.value.copy(error = null))
            return@LaunchedEffect
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { NavigationTopBar(
            label = stringResource(R.string.update_password),
            onBackClicked = navigateBack) },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(
                        start = AppTheme.dimens.paddingHorizontal,
                        end = AppTheme.dimens.paddingHorizontal,
                        top = padding.calculateTopPadding(),
                        bottom = padding.calculateBottomPadding()
                    ),
                verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.large),
                horizontalAlignment = Alignment.Start,
                content = {
                    PrimaryTextField(
                        text = oldPassword,
                        onValueChange = onOldPasswordValueChange,
                        placeHolder = stringResource(R.string.password_place_holder),
                        singleLine = true,
                        enabled = !state.value.isLoading,
                        visualTransformation = if (oldPasswordVisible.value) PasswordVisualTransformation() else VisualTransformation.None,
                        trailingIcon = {
                            PasswordTrailingIconComponent(oldPasswordVisible)
                        },
                        keyboardType = KeyboardType.Password,
                        label = stringResource(R.string.old_password),
                        showError = showError,
                        errorMessage = if (oldPassword.trim().isEmpty()) {
                            stringResource(id = R.string.password_empty_error_message)
                        } else {
                            null
                        }
                    )
                    PrimaryTextField(
                        text = newPassword,
                        onValueChange = onNewPasswordValueChange,
                        placeHolder = stringResource(R.string.password_place_holder),
                        singleLine = true,
                        enabled = !state.value.isLoading,
                        visualTransformation = if (newPasswordVisible.value) PasswordVisualTransformation() else VisualTransformation.None,
                        trailingIcon = {
                            PasswordTrailingIconComponent(newPasswordVisible)
                        },
                        keyboardType = KeyboardType.Password,
                        label = stringResource(R.string.new_password),
                        showError = showError,
                        errorMessage = if (newPassword.trim().isEmpty()) {
                            stringResource(id = R.string.password_empty_error_message)
                        } else {
                            null
                        }
                    )
                    PasswordConditionItem(
                        label = stringResource(id = R.string.at_least_6_characters),
                        success = newPassword.count() >= Constants.PASSWORD_LENGTH,
                    )
                    PasswordConditionItem(
                        label = stringResource(id = R.string.uppercase_characters),
                        success = newPassword.any { it.isUpperCase() },
                    )
                    PasswordConditionItem(
                        label = stringResource(id = R.string.numbers),
                        success = newPassword.any { it.isDigit() },
                    )
                    PrimaryButton(
                        text = stringResource(R.string.save),
                        enabled = !state.value.isLoading,
                        isLoading = state.value.isLoading,
                        onClick = {
                            showError = true
                            if (isValidateForm()) {
                                onEvent(
                                    UpdatePasswordEvent.OnUpdatePasswordClicked(
                                        oldPassword = oldPassword,
                                        newPassword = newPassword,
                                    )
                                )
                            }
                        },
                        isFormValidate = isValidateForm(),
                        iconSize = AppTheme.dimens.topBarIconStoreDetailsSize,
                    )
                },
            )
        },
    )
    PrimarySnackbar(snackbarHostState)
}


