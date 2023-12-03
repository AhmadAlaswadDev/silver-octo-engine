package com.bestcoders.zaheed.data.remote

import com.bestcoders.zaheed.data.remote.model.auth.AuthenticationResponse
import com.bestcoders.zaheed.data.remote.model.auth.DeleteUserResponse
import com.bestcoders.zaheed.data.remote.model.auth.GetUserRequest
import com.bestcoders.zaheed.data.remote.model.auth.GetUserResponse
import com.bestcoders.zaheed.data.remote.model.auth.LogoutResponse
import com.bestcoders.zaheed.data.remote.model.auth.SendVerificationCodeRequest
import com.bestcoders.zaheed.data.remote.model.auth.SendVerificationCodeResponse
import com.bestcoders.zaheed.data.remote.model.auth.SignupRequest
import com.bestcoders.zaheed.data.remote.model.auth.UpdatePasswordRequest
import com.bestcoders.zaheed.data.remote.model.auth.UpdatePasswordResponse
import com.bestcoders.zaheed.data.remote.model.auth.UpdateProfileRequest
import com.bestcoders.zaheed.data.remote.model.auth.UpdateProfileResponse
import com.bestcoders.zaheed.data.remote.model.auth.VerifyCodeRegisterResponse
import com.bestcoders.zaheed.data.remote.model.auth.VerifyCodeRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthenticationApi {

    // Login
    @POST("auth/otp/login-with-phone-req")
    suspend fun loginRequest(
        @Body request: SendVerificationCodeRequest
    ): Response<SendVerificationCodeResponse>

    // Signup
    @POST("auth/otp/signup-with-phone-exec")
    suspend fun signupRequest(
        @Body request: SignupRequest
    ): Response<AuthenticationResponse>

    // Verify Code Login
    @POST("auth/otp/authentication-exec")
    suspend fun validateOTPLoginRequest(
        @Body request: VerifyCodeRequest
    ): Response<AuthenticationResponse>

    // Verify Code Register
    @POST("auth/otp/validate-register-request")
    suspend fun validateOTPRegisterRequest(
        @Body request: VerifyCodeRequest
    ): Response<VerifyCodeRegisterResponse>

    // Resend OTP Login
    @POST("auth/otp/resend-otp-req")
    suspend fun resendOtpLoginRequest(
        @Body request: SendVerificationCodeRequest
    ): Response<SendVerificationCodeResponse>

    // Resend OTP Register
    @POST("auth/otp/resend-otp-registration-request")
    suspend fun resendOtpRegisterRequest(
        @Body request: SendVerificationCodeRequest
    ): Response<SendVerificationCodeResponse>

    // Get user
    @POST("get-user-by-access_token")
    suspend fun getUser(@Body request: GetUserRequest): Response<GetUserResponse>

    // Logout
    @GET("auth/logout")
    suspend fun logoutUser(
        @Header("Authorization") token: String?,
    ): Response<LogoutResponse>


    // Update profile
    @POST("profile/update")
    suspend fun updateProfile(
        @Header("Authorization") token: String?,
        @Body updateProfileRequest: UpdateProfileRequest
    ): Response<UpdateProfileResponse>


    // Update password
    @POST("profile/change-password")
    suspend fun updatePassword(
        @Header("Authorization") token: String?,
        @Body updatePasswordRequest: UpdatePasswordRequest
    ): Response<UpdatePasswordResponse>

    // Delete user
    @GET("profile/delete/req")
    suspend fun deleteUser(
        @Header("Authorization") token: String?,
    ): Response<DeleteUserResponse>

    // Authenticate delete user
    @POST("profile/delete/exec")
    suspend fun validateOTPDeleteUserRequest(
        @Header("Authorization") token: String?,
        @Body request: VerifyCodeRequest
    ): Response<DeleteUserResponse>

}


