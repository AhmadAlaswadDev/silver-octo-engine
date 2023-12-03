package com.bestcoders.zaheed.data.remote.model.auth

data class UpdatePasswordResponse(
    val success: Boolean,
    val data: UserResponse? = null,
    val message: String,
    val err: Map<String, List<String>>? = null,
)