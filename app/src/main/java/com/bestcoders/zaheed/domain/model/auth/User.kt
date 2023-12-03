package com.bestcoders.zaheed.domain.model.auth

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val avatar: String? = null,
    val avatarOriginal: String,
    val gender: String?,
    val birthDate: String?,
    val phone: String,
    val saved: String,
    val type: String? = null,
    val accessToken: String? = null,
    val expiresAt: String? = null,
    val tokenType: String? = null,
)