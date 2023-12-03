package com.bestcoders.zaheed.data.remote.model.auth

import com.google.gson.annotations.SerializedName

data class GetUserRequest(
    @SerializedName("access_token") val token: String,
)