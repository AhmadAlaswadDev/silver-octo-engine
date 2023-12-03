package com.bestcoders.zaheed.data.remote.model.auth

import com.google.gson.annotations.SerializedName

data class SendVerificationCodeRequest(
    @SerializedName("phone_number") val phoneNumber: String?,
)