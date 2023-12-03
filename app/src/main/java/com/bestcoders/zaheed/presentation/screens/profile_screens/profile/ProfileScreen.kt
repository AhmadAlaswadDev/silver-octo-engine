package com.bestcoders.zaheed.presentation.screens.profile_screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bestcoders.zaheed.core.util.UiEvent
import com.bestcoders.zaheed.domain.model.auth.VerifyCodeType
import com.bestcoders.zaheed.presentation.components.PrimarySnackbar
import com.bestcoders.zaheed.presentation.screens.profile_screens.profile.components.ProfileHeaderComponent
import com.bestcoders.zaheed.presentation.screens.profile_screens.profile.components.ProfileScreenBody
import com.bestcoders.zaheed.presentation.screens.profile_screens.profile.components.ProfileSubHeaderComponent
import com.bestcoders.zaheed.ui.theme.AppTheme
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ProfileScreen(
    state: MutableState<ProfileState>,
    onEvent: (ProfileEvent) -> Unit,
    uiEvent: SharedFlow<UiEvent>,
    resetState: (ProfileState) -> Unit,
    navigateToLoginScreen: () -> Unit,
    paddingValues: PaddingValues,
    navigateToSignInScreen: () -> Unit,
    navigateToEditProfileScreen: () -> Unit,
    navigateToUpdatePasswordScreen: () -> Unit,
    navigateToOrderHistoryScreen: () -> Unit,
    navigateToAboutUsScreen: () -> Unit,
    navigateToFAQScreen: () -> Unit,
    navigateToPrivacyPoliciesScreen: (String) -> Unit,
) {
    val snackbarHostState = remember {
        SnackbarHostState()
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

    LaunchedEffect(state.value.userLoggedOut) {
        if (state.value.userLoggedOut) {
            navigateToLoginScreen()
        }
    }

    LaunchedEffect(state.value.userLoggedOutError) {
        if (!state.value.userLoggedOut && !state.value.userLoggedOutError.isNullOrEmpty()) {
            onEvent(ProfileEvent.ShowSnackBar(message = state.value.userLoggedOutError.toString()))
            resetState(state.value.copy(userLoggedOutError = null))
            return@LaunchedEffect
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(
                        start = AppTheme.dimens.paddingHorizontal,
                        end = AppTheme.dimens.paddingHorizontal,
                        top = AppTheme.dimens.paddingVertical,
                        bottom = paddingValues.calculateBottomPadding()
                    ),
                verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.large),
                horizontalAlignment = Alignment.Start,
                content = {
                    ProfileHeaderComponent()
                    ProfileSubHeaderComponent(
                        navigateToOrderHistoryScreen = navigateToOrderHistoryScreen,
                    )
                    ProfileScreenBody(
                        onEvent = onEvent,
                        navigateToSignInScreen = navigateToSignInScreen,
                        navigateToEditProfileScreen = navigateToEditProfileScreen,
                        navigateToUpdatePasswordScreen = navigateToUpdatePasswordScreen,
                        navigateToAboutUsScreen = navigateToAboutUsScreen,
                        navigateToFAQScreen = navigateToFAQScreen,
                        navigateToPrivacyPoliciesScreen = navigateToPrivacyPoliciesScreen
                    )
                },
            )
        },
    )
    PrimarySnackbar(snackbarHostState)
}
