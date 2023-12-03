package com.bestcoders.zaheed.data.remote.model.auth

data class LogoutResponse(
    val success: Boolean,
    val message: String,
    val err: Map<String, List<String>>? = null,
)