package com.bestcoders.zaheed.domain.use_case

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.core.util.Resource
import com.bestcoders.zaheed.data.local.entity.UserAuthDataStoreEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class CheckUserLoggedInUseCase @Inject constructor(
    private val dataStore: DataStore<UserAuthDataStoreEntity>,
    @ApplicationContext private val context: Context
) {

    operator fun invoke(): Flow<Resource<UserAuthDataStoreEntity>> = channelFlow {
        send(Resource.Loading())
        try {
            dataStore.data.collectLatest { local ->
                // check if user logged in in productDetailsDataResponse store
                if (!local.userToken.isNullOrEmpty()) {
                    // if logged in success
                    send(Resource.Success(local))
                } else {
                    // if not logged in then error 'user not logged in'
                    send(Resource.Error(message = context.getString(R.string.user_not_logged_in), data = null))
                }
            }
        } catch (e: Exception) {
            Log.d(Constants.EXCEPTION_TAG, Log.getStackTraceString(e.cause))
        }
    }

}