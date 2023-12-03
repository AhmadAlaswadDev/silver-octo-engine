package com.bestcoders.zaheed.data.remote.model.auth

import com.google.gson.annotations.SerializedName

data class VerifyCodeRequest(
    @SerializedName("phone_number") val phoneNumber: String? = null,
    @SerializedName("verification_code") val verificationCode: String,
)