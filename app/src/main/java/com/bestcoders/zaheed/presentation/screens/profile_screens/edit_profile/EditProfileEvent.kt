package com.bestcoders.zaheed.presentation.screens.profile_screens.edit_profile

import com.bestcoders.zaheed.presentation.screens.profile_screens.profile.ProfileEvent

sealed interface EditProfileEvent {
    data class ShowSnackBar(val message: String) : EditProfileEvent
    data class OnEditClicked(
        val name: String,
        val email: String,
        val phoneNumber: String,
        val birthDate: String,
        val gender: String,
    ) : EditProfileEvent
    data object OnDeleteUserClicked : EditProfileEvent
}