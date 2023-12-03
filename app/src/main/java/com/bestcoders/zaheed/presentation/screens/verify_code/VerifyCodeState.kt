package com.bestcoders.zaheed.presentation.screens.verify_code

import com.bestcoders.zaheed.domain.model.auth.User

data class VerifyCodeState(
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val reSendCodeSuccess: Boolean = false,
    val verifyError: String? = null,
    val reSendCodeError: String? = null,
    val verifyDeleteUserSuccess: Boolean = false,
    val verifyDeleteUserError: String? = null,
    val user: User? = null,
    val phoneNumber: String? = null,
    val verifyCodeType: Int? = null,
    val registrationRequestId: Int? = null,
)
