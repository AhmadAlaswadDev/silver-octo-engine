package com.bestcoders.zaheed.presentation.screens.profile_screens.about_us


sealed interface AboutUsEvent {
    data class ShowSnackBar(val message: String) : AboutUsEvent

    object GetAboutUs : AboutUsEvent


}