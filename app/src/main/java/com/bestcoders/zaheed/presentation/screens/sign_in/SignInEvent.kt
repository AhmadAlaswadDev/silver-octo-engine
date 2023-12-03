package com.bestcoders.zaheed.presentation.screens.sign_in


sealed interface SignInEvent {

    data class OnSendVerificationCodeClicked(
        val phoneNumber: String,
    ) : SignInEvent




}