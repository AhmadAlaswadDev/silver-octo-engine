package com.bestcoders.zaheed.data.local.entity

import kotlinx.serialization.Serializable


@Serializable
data class UserAuthDataStoreEntity(
    val userToken: String? = null,
    val tempToken: String? = null,
    val name: String? = null,
    val phoneNumber: String? = null,
    val email: String? = null,
    val gender: String? = null,
    val birthDate: String? = null,
    val saved: String? = null,
)
