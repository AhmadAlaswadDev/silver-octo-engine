package com.bestcoders.zaheed.domain.use_case

import android.content.Context
import android.util.Log
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.core.util.NetworkUtils
import com.bestcoders.zaheed.core.util.Resource
import com.bestcoders.zaheed.domain.model.auth.SessionData
import com.bestcoders.zaheed.domain.repository.AuthRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class ReSendVerificationCodeRegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    @ApplicationContext private val context: Context
) {
    operator fun invoke(
        phoneNumber: String,
    ): Flow<Resource<SessionData>> = flow {
        emit(Resource.Loading())
        if (!NetworkUtils.isNetworkAvailable(context)) {
            return@flow emit(Resource.Error(context.getString(R.string.no_internet_connection)))
        }
        try {
            val result = authRepository.resendOtpRegister(phoneNumber = phoneNumber,)
            return@flow emit(result)
        } catch (e: Exception) {
            Log.e(Constants.EXCEPTION_TAG, e.printStackTrace().toString())
        }
    }
}
