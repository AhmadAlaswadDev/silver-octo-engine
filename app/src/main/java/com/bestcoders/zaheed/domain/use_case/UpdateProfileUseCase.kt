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


class UpdateProfileUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val dataStore: DataStore<UserAuthDataStoreEntity>,
    @ApplicationContext private val context: Context
) {
    operator fun invoke(
        name: String? = null,
        email: String? = null,
        phoneNumber: String? = null,
        birthDate: String? = null,
        gender: String? = null,
    ): Flow<Resource<User>> = flow {
        emit(Resource.Loading())
        if (!NetworkUtils.isNetworkAvailable(context)) {
            return@flow emit(Resource.Error(context.getString(R.string.no_internet_connection)))
        }
        try {
            val result = authRepository.updateProfile(
                name = name,
                email = email,
                phoneNumber = phoneNumber,
                birthDate = birthDate,
                gender = gender
            )
            if (result.data != null) {
                Constants.userName = result.data.name
                Constants.userPhone = result.data.phone
                Constants.userEmail = result.data.email
                Constants.userGender = result.data.gender!!
                Constants.userBirthDate = result.data.birthDate!!
                UserAuthDataStoreManager.setUserAuthData(
                    dataStore = dataStore,
                    userAuthDataStoreEntity = UserAuthDataStoreEntity(
                        userToken = Constants.userToken,
                        name = result.data.name,
                        phoneNumber = result.data.phone,
                        email = result.data.email,
                        gender = result.data.gender,
                        birthDate = result.data.birthDate,
                        saved = Constants.userSaved,
                        tempToken  = Constants.tempToken,
                    )
                )
            }
            return@flow emit(result)
        } catch (e: Exception) {
            Log.e(Constants.EXCEPTION_TAG, e.printStackTrace().toString())
        }
    }
}
