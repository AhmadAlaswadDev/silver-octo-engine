package com.bestcoders.zaheed.presentation.screens.profile_screens.profile

import android.content.Context


sealed interface ProfileEvent {
    data object OnLogOutClicked : ProfileEvent
    data class ChangeLanguage(val language: String, val context: Context) : ProfileEvent
    data class OnContactDetailsClicked(val context: Context) : ProfileEvent
    data class ShowSnackBar(val message: String) : ProfileEvent

}