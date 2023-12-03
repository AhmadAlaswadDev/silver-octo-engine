package com.bestcoders.zaheed.data.remote.model.auth

import com.google.gson.annotations.SerializedName

data class UpdateProfileRequest(
    @SerializedName("name") val name: String? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("phone_number") val phoneNumber: String? = null,
    @SerializedName("birth_date") val birthDate: String? = null,
    @SerializedName("gender") val gender: String? = null,
)