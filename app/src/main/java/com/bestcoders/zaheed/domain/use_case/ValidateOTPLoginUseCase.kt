package com.bestcoders.zaheed.domain.use_case

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.core.util.NetworkUtils
import com.bestcoders.zaheed.core.util.Resource
import com.bestcoders.zaheed.data.local.data_store.UserAuthDataStoreManager
import com.bestcoders.zaheed.data.local.entity.UserAuthDataStoreEntity
import com.bestcoders.zaheed.domain.model.auth.User
import com.bestcoders.zaheed.domain.repository.AuthRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ValidateOTPLoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val dataStore: DataStore<UserAuthDataStoreEntity>,
    @ApplicationContext private val context: Context
) {
    operator fun invoke(
        phoneNumber: String,
        verificationCode: String,
    ): Flow<Resource<User>> = flow {
        emit(Resource.Loading())

        if (!NetworkUtils.isNetworkAvailable(context)) {
            return@flow emit(Resource.Error(context.getString(R.string.no_internet_connection)))
        }

        try {
            val result = authRepository.validateOTPLogin(
                phoneNumber = phoneNumber,
                verificationCode = verificationCode
            )
            if (result.data != null) {
                UserAuthDataStoreManager.setUserAuthData(
                    dataStore = dataStore,
                    userAuthDataStoreEntity = UserAuthDataStoreEntity(
                        userToken = result.data.accessToken,
                        name = result.data.name,
                        phoneNumber = result.data.phone,
                        saved = result.data.saved,
                        email = result.data.email,
                        gender = result.data.gender,
                        birthDate = result.data.birthDate,
                    )
                )
            }
            return@flow emit(result)
        } catch (e: Exception) {
            Log.d(Constants.EXCEPTION_TAG, Log.getStackTraceString(e.cause))
        }
    }
}
