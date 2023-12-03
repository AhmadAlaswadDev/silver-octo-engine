package com.bestcoders.zaheed.presentation.screens.verify_code

import android.content.Context


sealed interface VerifyCodeEvent {

    data class ShowSnackBar(val message: String): VerifyCodeEvent

    data class OnReSendCodeLoginClicked(
        val phoneNumber: String,
        val context:Context
    ) : VerifyCodeEvent

    data class VerifyCodeLogin(
        val phoneNumber: String,
        val verificationCode: String,
        val context:Context
    ) : VerifyCodeEvent


    data class OnReSendCodeRegisterClicked(
        val phoneNumber: String,
    ) : VerifyCodeEvent

    data class VerifyCodeRegister(
        val phoneNumber: String,
        val verificationCode: String,
    ) : VerifyCodeEvent

    data class VerifyCodeDeleteUser(
        val verificationCode: String,
    ) : VerifyCodeEvent


}