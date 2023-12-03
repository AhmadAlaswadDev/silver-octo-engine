package com.bestcoders.zaheed.data.remote.model.auth

import com.google.gson.annotations.SerializedName

data class UpdatePasswordRequest(
    @SerializedName("old_password") val oldPassword: String,
    @SerializedName("new_password") val newPassword: String,
)