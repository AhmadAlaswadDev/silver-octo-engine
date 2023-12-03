package com.bestcoders.zaheed.presentation.screens.signup

import com.bestcoders.zaheed.domain.model.auth.User

data class SignupState(
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val user: User? = null,
    val error: String? = null,
)
