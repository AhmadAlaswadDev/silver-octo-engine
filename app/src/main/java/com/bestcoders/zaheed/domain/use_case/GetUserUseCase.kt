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


class GetUserUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val dataStore: DataStore<UserAuthDataStoreEntity>,
    @ApplicationContext private val context: Context,
) {
    operator fun invoke(
        token: String,
    ): Flow<Resource<User>> = flow {
        emit(Resource.Loading())
        if (!NetworkUtils.isNetworkAvailable(context)) {
            return@flow emit(Resource.Error(context.getString(R.string.no_internet_connection)))
        }
        try {
            val result = authRepository.getUser(token = token)
            UserAuthDataStoreManager.setUserAuthData(
                dataStore = dataStore,
                userAuthDataStoreEntity =  UserAuthDataStoreEntity(
                    userToken = Constants.userToken,
                    saved = result.data!!.saved,
                    name = Constants.userName,
                    phoneNumber = Constants.userPhone,
                    email = Constants.userEmail,
                    gender = Constants.userGender,
                    birthDate = Constants.userBirthDate,
                )
            )
            return@flow emit(result)
        } catch (e: Exception) {
            Log.e(Constants.EXCEPTION_TAG, e.printStackTrace().toString())
        }
    }
}
