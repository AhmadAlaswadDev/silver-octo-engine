package com.bestcoders.zaheed.data.repository

import android.content.Context
import android.util.Log
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.core.util.Resource
import com.bestcoders.zaheed.core.util.network.getMessageFromRequestCode
import com.bestcoders.zaheed.data.remote.AuthenticationApi
import com.bestcoders.zaheed.data.remote.model.auth.GetUserRequest
import com.bestcoders.zaheed.data.remote.model.auth.SendVerificationCodeRequest
import com.bestcoders.zaheed.data.remote.model.auth.SignupRequest
import com.bestcoders.zaheed.data.remote.model.auth.UpdatePasswordRequest
import com.bestcoders.zaheed.data.remote.model.auth.UpdateProfileRequest
import com.bestcoders.zaheed.data.remote.model.auth.VerifyCodeRequest
import com.bestcoders.zaheed.domain.model.auth.SessionData
import com.bestcoders.zaheed.domain.model.auth.User
import com.bestcoders.zaheed.domain.repository.AuthRepository


class AuthRepositoryImpl(
    private val api: AuthenticationApi,
    private val context: Context,
) : AuthRepository {
    override suspend fun login(
        phoneNumber: String,
    ): Resource<SessionData> {
        return try {
            val response = api.loginRequest(
                request = SendVerificationCodeRequest(
                    phoneNumber = phoneNumber,
                )
            )
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success) {
                    Resource.Success(body.toSessionData())
                } else {
                    Log.e(
                        Constants.EXCEPTION_TAG,
                        body?.message + getMessageFromRequestCode(response.code())
                    )
                    Resource.Error(body?.err?.firstNotNullOfOrNull { it.value[0] }
                        ?: context.getString(R.string.un_known_error))
                }
            } else {
                Log.e(Constants.EXCEPTION_TAG, getMessageFromRequestCode(response.code()))
                Resource.Error(context.getString(R.string.un_known_error))
            }
        } catch (e: Exception) {
            Log.e(Constants.EXCEPTION_TAG, e.printStackTrace().toString())
            Resource.Error(context.getString(R.string.un_known_error))
        }
    }

    override suspend fun signup(
        request: SignupRequest
    ): Resource<User> {
        return try {
            val response = api.signupRequest(
                request = request
            )
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success) {
                    Resource.Success(body.toUser())
                } else {
                    Log.e(
                        Constants.EXCEPTION_TAG,
                        body?.message + getMessageFromRequestCode(
                            response.code()
                        )
                    )
                    Resource.Error(body?.err?.firstNotNullOfOrNull { it.value[0] }
                        ?: context.getString(R.string.un_known_error))
                }
            } else {
                Log.e(
                    Constants.EXCEPTION_TAG,
                    getMessageFromRequestCode(response.code())
                )
                Resource.Error(context.getString(R.string.un_known_error))
            }
        } catch (e: Exception) {
            Log.e(Constants.EXCEPTION_TAG, e.printStackTrace().toString())
            Resource.Error(context.getString(R.string.un_known_error))
        }
    }

    override suspend fun resendOtpLogin(
        phoneNumber: String,
    ): Resource<SessionData> {
        return try {
            val response = api.resendOtpLoginRequest(
                request = SendVerificationCodeRequest(
                    phoneNumber = phoneNumber,
                )
            )
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success) {
                    Resource.Success(body.toSessionData())
                } else {
                    Log.e(
                        Constants.EXCEPTION_TAG,
                        body?.message + getMessageFromRequestCode(response.code())
                    )
                    Resource.Error(body?.err?.firstNotNullOfOrNull { it.value[0] }
                        ?: context.getString(R.string.un_known_error))
                }
            } else {
                Log.e(Constants.EXCEPTION_TAG, getMessageFromRequestCode(response.code()))
                Resource.Error(context.getString(R.string.un_known_error))
            }
        } catch (e: Exception) {
            Log.e(Constants.EXCEPTION_TAG, "${e.printStackTrace()}")
            Resource.Error(context.getString(R.string.un_known_error))
        }
    }

    override suspend fun resendOtpRegister(
        phoneNumber: String,
    ): Resource<SessionData> {
        return try {
            val response = api.resendOtpRegisterRequest(
                request = SendVerificationCodeRequest(
                    phoneNumber = phoneNumber,
                )
            )
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success) {
                    Resource.Success(body.toSessionData())
                } else {
                    Log.e(
                        Constants.EXCEPTION_TAG,
                        body?.message + getMessageFromRequestCode(response.code())
                    )
                    Resource.Error(body?.err?.firstNotNullOfOrNull { it.value[0] }
                        ?: context.getString(R.string.un_known_error))
                }
            } else {
                Log.e(Constants.EXCEPTION_TAG, getMessageFromRequestCode(response.code()))
                Resource.Error(context.getString(R.string.un_known_error))
            }
        } catch (e: Exception) {
            Log.e(Constants.EXCEPTION_TAG, "${e.printStackTrace()}")
            Resource.Error(context.getString(R.string.un_known_error))
        }
    }

    override suspend fun validateOTPLogin(
        phoneNumber: String,
        verificationCode: String
    ): Resource<User> {
        return try {
            val response = api.validateOTPLoginRequest(
                request = VerifyCodeRequest(
                    phoneNumber = phoneNumber,
                    verificationCode = verificationCode
                )
            )
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success) {
                    Log.e(
                        "ASDASD",
                        body.access_token.toString()
                    )
                    Resource.Success(body.toUser())
                } else {
                    Log.e(
                        Constants.EXCEPTION_TAG,
                        getMessageFromRequestCode(
                            response.code()
                        )
                    )
                    Log.e(
                        Constants.EXCEPTION_TAG,
                        body?.success.toString() + body?.message + "\n ${body?.err.toString()}" + getMessageFromRequestCode(
                            response.code()
                        )
                    )
                    Resource.Error(body?.err?.firstNotNullOfOrNull { it.value[0] }
                        ?: context.getString(R.string.un_known_error))
                }
            } else {
                Log.e(
                    Constants.EXCEPTION_TAG,
                    getMessageFromRequestCode(response.code())
                )
                Resource.Error(context.getString(R.string.un_known_error))
            }
        } catch (e: Exception) {
            Log.e(
                Constants.EXCEPTION_TAG,
                e.printStackTrace().toString()
            )
            Resource.Error(context.getString(R.string.un_known_error))
        }
    }

    override suspend fun validateOTPRegister(
        phoneNumber: String,
        verificationCode: String
    ): Resource<Int> {
        return try {
            val response = api.validateOTPRegisterRequest(
                request = VerifyCodeRequest(
                    phoneNumber = phoneNumber,
                    verificationCode = verificationCode
                )
            )
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success && body.data?.registration_request_id != null) {
                    Resource.Success(body.data.registration_request_id)
                } else {
                    Log.e(
                        Constants.EXCEPTION_TAG,
                        body?.success.toString() + body?.message + "\n ${body?.err.toString()}" + getMessageFromRequestCode(
                            response.code()
                        )
                    )
                    Resource.Error(body?.err?.firstNotNullOfOrNull { it.value[0] }
                        ?: context.getString(R.string.un_known_error))
                }
            } else {
                Log.e(Constants.EXCEPTION_TAG, getMessageFromRequestCode(response.code()))
                Resource.Error(context.getString(R.string.un_known_error))
            }
        } catch (e: Exception) {
            Log.e(Constants.EXCEPTION_TAG, e.printStackTrace().toString())
            Resource.Error(context.getString(R.string.un_known_error))
        }
    }

    override suspend fun getUser(token: String): Resource<User> {
        return try {
            val response = api.getUser(
                request = GetUserRequest(token = token)
            )
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success) {
                    Resource.Success(body.toUser())
                } else {
                    Log.e(
                        Constants.EXCEPTION_TAG,
                        body?.err?.values.toString() + body?.message + getMessageFromRequestCode(
                            response.code()
                        )
                    )
                    Resource.Error(body?.err?.firstNotNullOfOrNull { it.value[0] }
                        ?: context.getString(R.string.un_known_error))
                }
            } else {
                Log.e(Constants.EXCEPTION_TAG, getMessageFromRequestCode(response.code()))
                Resource.Error(context.getString(R.string.un_known_error))
            }
        } catch (e: Exception) {
            Log.e(Constants.EXCEPTION_TAG, "${e.printStackTrace()}")
            Resource.Error(context.getString(R.string.un_known_error))
        }
    }

    override suspend fun logoutUser(): Resource<String> {
        return try {
            val response = api.logoutUser(
                token = if (Constants.userToken.isEmpty()) {
                    null
                } else {
                    Constants.BEARER_TOKEN + Constants.userToken
                }
            )
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success) {
                    Resource.Success(body.message)
                } else {
                    Log.e(
                        Constants.EXCEPTION_TAG,
                        body?.err?.values.toString() + getMessageFromRequestCode(response.code())
                    )
                    Resource.Error(body?.err?.firstNotNullOfOrNull { it.value[0] }
                        ?: context.getString(R.string.un_known_error))
                }
            } else {
                Log.e(Constants.EXCEPTION_TAG, getMessageFromRequestCode(response.code()))
                Resource.Error(context.getString(R.string.un_known_error))
            }
        } catch (e: Exception) {
            Log.e(Constants.EXCEPTION_TAG, "${e.printStackTrace()}")
            Resource.Error(context.getString(R.string.un_known_error))
        }
    }

    override suspend fun updateProfile(
        name: String?,
        email: String?,
        phoneNumber: String?,
        birthDate: String?,
        gender: String?,
    ): Resource<User> {
        return try {
            val response = api.updateProfile(
                token = if (Constants.userToken.isEmpty()) {
                    null
                } else {
                    Constants.BEARER_TOKEN + Constants.userToken
                },
                updateProfileRequest = UpdateProfileRequest(
                    name = name,
                    email = email,
                    phoneNumber = phoneNumber,
                    birthDate = birthDate,
                    gender = gender
                )
            )
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success && body.data != null) {
                    Resource.Success(body.data.toUser())
                } else {
                    Log.e(
                        Constants.EXCEPTION_TAG,
                        body?.message + getMessageFromRequestCode(
                            response.code()
                        )
                    )
                    Resource.Error(body?.err?.firstNotNullOfOrNull { it.value[0] }
                        ?: context.getString(R.string.un_known_error))
                }
            } else {
                Log.e(
                    Constants.EXCEPTION_TAG,
                    getMessageFromRequestCode(response.code())
                )
                Resource.Error(context.getString(R.string.un_known_error))
            }
        } catch (e: Exception) {
            Log.e(Constants.EXCEPTION_TAG, e.printStackTrace().toString())
            Resource.Error(context.getString(R.string.un_known_error))
        }
    }


    override suspend fun updatePassword(
        oldPassword: String,
        newPassword: String,
    ): Resource<Unit> {
        return try {
            val response = api.updatePassword(
                token = if (Constants.userToken.isEmpty()) {
                    null
                } else {
                    Constants.BEARER_TOKEN + Constants.userToken
                },
                updatePasswordRequest = UpdatePasswordRequest(
                    oldPassword = oldPassword,
                    newPassword = newPassword,
                )
            )
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success) {
                    Resource.Success(Unit)
                } else {
                    Log.e(
                        Constants.EXCEPTION_TAG,
                        body?.message + getMessageFromRequestCode(
                            response.code()
                        )
                    )
                    Resource.Error(body?.err?.firstNotNullOfOrNull { it.value[0] }
                        ?: context.getString(R.string.un_known_error))
                }
            } else {
                Log.e(
                    Constants.EXCEPTION_TAG,
                    getMessageFromRequestCode(response.code())
                )
                Resource.Error(context.getString(R.string.un_known_error))
            }
        } catch (e: Exception) {
            Log.e(Constants.EXCEPTION_TAG, e.printStackTrace().toString())
            Resource.Error(context.getString(R.string.un_known_error))
        }
    }

    override suspend fun deleteUser(): Resource<Unit> {
        return try {
            val response = api.deleteUser(
                token = if (Constants.userToken.isEmpty()) {
                    null
                } else {
                    Constants.BEARER_TOKEN + Constants.userToken
                },
            )
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success) {
                    Resource.Success(Unit)
                } else {
                    Log.e(
                        Constants.EXCEPTION_TAG,
                        body?.message + getMessageFromRequestCode(
                            response.code()
                        )
                    )
                    Resource.Error(body?.err?.firstNotNullOfOrNull { it.value[0] }
                        ?: context.getString(R.string.un_known_error))
                }
            } else {
                Log.e(
                    Constants.EXCEPTION_TAG,
                    getMessageFromRequestCode(response.code())
                )
                Resource.Error(context.getString(R.string.un_known_error))
            }
        } catch (e: Exception) {
            Log.e(Constants.EXCEPTION_TAG, e.printStackTrace().toString())
            Resource.Error(context.getString(R.string.un_known_error))
        }
    }

    override suspend fun validateOTPDeleteUser(verificationCode: String): Resource<Unit> {
        return try {
            val response = api.validateOTPDeleteUserRequest(
                token = if (Constants.userToken.isEmpty()) {
                    null
                } else {
                    Constants.BEARER_TOKEN + Constants.userToken
                },
                request = VerifyCodeRequest(verificationCode = verificationCode)
            )
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success) {
                    Resource.Success(Unit)
                } else {
                    Log.e(
                        Constants.EXCEPTION_TAG,
                        body?.message + getMessageFromRequestCode(
                            response.code()
                        )
                    )
                    Resource.Error(body?.err?.firstNotNullOfOrNull { it.value[0] }
                        ?: context.getString(R.string.un_known_error))
                }
            } else {
                Log.e(
                    Constants.EXCEPTION_TAG,
                    getMessageFromRequestCode(response.code())
                )
                Resource.Error(context.getString(R.string.un_known_error))
            }
        } catch (e: Exception) {
            Log.e(Constants.EXCEPTION_TAG, e.printStackTrace().toString())
            Resource.Error(context.getString(R.string.un_known_error))
        }
    }


}