package com.bestcoders.zaheed.presentation.screens.profile_screens.edit_profile

data class EditProfileState(
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val error: String? = null,
    val deleteUserSuccess: Boolean = false,
    val deleteUserError: String? = null,
)
