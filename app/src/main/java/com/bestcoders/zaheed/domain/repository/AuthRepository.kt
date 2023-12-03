package com.bestcoders.zaheed.domain.repository

import com.bestcoders.zaheed.core.util.Resource
import com.bestcoders.zaheed.data.remote.model.auth.SignupRequest
import com.bestcoders.zaheed.domain.model.auth.SessionData
import com.bestcoders.zaheed.domain.model.auth.User

interface AuthRepository {

    suspend fun login(
        phoneNumber: String,
    ): Resource<SessionData>

    suspend fun signup(
        request: SignupRequest
    ): Resource<User>

    suspend fun resendOtpLogin(
        phoneNumber: String,
    ): Resource<SessionData>

    suspend fun resendOtpRegister(
        phoneNumber: String,
    ): Resource<SessionData>

    suspend fun validateOTPLogin(
        phoneNumber: String,
        verificationCode: String,
    ): Resource<User>

    suspend fun validateOTPRegister(
        phoneNumber: String,
        verificationCode: String,
    ): Resource<Int>

    suspend fun getUser(
        token: String,
    ): Resource<User>

    suspend fun logoutUser(): Resource<String>

    suspend fun updateProfile(
        name: String? = null,
        email: String? = null,
        phoneNumber: String? = null,
        birthDate: String? = null,
        gender: String? = null,
    ): Resource<User>

    suspend fun updatePassword(
        oldPassword: String,
        newPassword: String,
    ): Resource<Unit>

    suspend fun deleteUser(): Resource<Unit>

    suspend fun validateOTPDeleteUser(verificationCode: String): Resource<Unit>


}