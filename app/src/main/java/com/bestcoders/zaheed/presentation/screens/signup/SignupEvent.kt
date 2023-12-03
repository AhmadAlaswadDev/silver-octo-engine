package com.bestcoders.zaheed.presentation.screens.signup

import com.bestcoders.zaheed.domain.model.auth.SignupData

sealed interface SignupEvent {


    data class ShowSnackBar(val message: String): SignupEvent

    data class OnSignupClicked(
        val signupData: SignupData
    ) : SignupEvent
}