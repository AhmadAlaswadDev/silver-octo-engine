package com.bestcoders.zaheed.presentation.screens.profile_screens.update_password


sealed interface UpdatePasswordEvent {
    data class ShowSnackBar(val message: String) : UpdatePasswordEvent

    data class OnUpdatePasswordClicked(val oldPassword: String, val newPassword: String) :
        UpdatePasswordEvent

}