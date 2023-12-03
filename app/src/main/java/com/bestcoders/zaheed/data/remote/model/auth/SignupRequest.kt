package com.bestcoders.zaheed.data.remote.model.auth

import com.google.gson.annotations.SerializedName

data class SignupRequest(
    @SerializedName("birth_date") val birthDate: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("password_confirmation") val passwordConfirmation: String,
    @SerializedName("phone_number") val phoneNumber: String,
    @SerializedName("registration_request_id") val registrationRequestId: String,
)

