package com.bestcoders.zaheed.domain.use_case

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.core.util.NetworkUtils
import com.bestcoders.zaheed.core.util.Resource
import com.bestcoders.zaheed.data.local.entity.UserAuthDataStoreEntity
import com.bestcoders.zaheed.domain.model.auth.User
import com.bestcoders.zaheed.domain.repository.AuthRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class SignupVerifyCodeUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val dataStore: DataStore<UserAuthDataStoreEntity>,
    @ApplicationContext private val context: Context
) {
    operator fun invoke(
        lang: String,
        sessionId: String,
        verificationCode: String,
    ): Flow<Resource<User>> = flow {
        emit(Resource.Loading())

        if (verificationCode.trim().isEmpty()) {
            return@flow emit(Resource.Error(context.getString(R.string.verification_code_require)))
        }

        if (verificationCode.length != 6) {
            return@flow emit(
                Resource.Error(
                    context.getString(R.string.verification_code_should_be_6_digits),
                    null
                )
            )
        }

        if (!NetworkUtils.isNetworkAvailable(context)) {
            return@flow emit(Resource.Error(context.getString(R.string.no_internet_connection)))
        }

        try {
//            val result = authRepository.signupVerifyCodeRequest(
//                lang = lang,
//                sessionId = sessionId,
//                verificationCode = verificationCode
//            )
//            if (result.productDetailsDataResponse != null && result.productDetailsDataResponse.success) {
//                UserAuthDataStoreManager.setUserAuthData(
//                    dataStore = dataStore,
//                    userAuthDataStoreEntity = UserAuthDataStoreEntity(userToken = result.productDetailsDataResponse.accessToken)
//                )
//                return@flow emit(Resource.Success(result.productDetailsDataResponse))
//            } else {
//                return@flow emit(Resource.Error(result.productDetailsDataResponse?.message ?: "Something went wrong!"))
//            }
        } catch (e: Exception) {
            Log.d(Constants.EXCEPTION_TAG, Log.getStackTraceString(e.cause))
        }
    }
}
