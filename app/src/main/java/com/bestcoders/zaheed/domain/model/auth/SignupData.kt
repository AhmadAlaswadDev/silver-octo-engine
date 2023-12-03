package com.bestcoders.zaheed.domain.model.auth

import com.bestcoders.zaheed.data.remote.model.auth.SignupRequest

data class SignupData(
    val lang: String,
    val phoneNumber: String,
    val birthDate: String,
    val gender: String,
    val name: String,
    val email: String,
    val password: String,
    val passwordConfirmation: String,
    val registrationRequestId: String,
) {
    fun toSendVerificationCodeSignupRequest(): SignupRequest {
        return SignupRequest(
            birthDate = this.birthDate,
            gender = this.gender,
            name = this.name,
            email = this.email,
            password = this.password,
            passwordConfirmation = this.passwordConfirmation,
            phoneNumber = this.phoneNumber,
            registrationRequestId = this.registrationRequestId
        )
    }
}
