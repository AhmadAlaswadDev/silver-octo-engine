package com.bestcoders.zaheed.presentation.screens.sign_in

data class SignInState(
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val error: String? = null,
)
