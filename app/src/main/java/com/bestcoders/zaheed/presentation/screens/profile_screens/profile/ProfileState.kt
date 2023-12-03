package com.bestcoders.zaheed.presentation.screens.profile_screens.profile

data class ProfileState(
    val isLoading: Boolean = false,
    val userLoggedOut: Boolean = false,
    val userLoggedOutError: String? = null,
)
