package com.bestcoders.zaheed.domain.use_case

import android.content.Context
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.util.NetworkUtils
import com.bestcoders.zaheed.core.util.Resource
import com.bestcoders.zaheed.domain.model.auth.User
import com.bestcoders.zaheed.domain.repository.AuthRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class RecaptchaUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    @ApplicationContext private val context: Context
) {
    operator fun invoke(
        token: String,
    ): Flow<Resource<User>> = flow {
        emit(Resource.Loading())

        if (!NetworkUtils.isNetworkAvailable(context)) {
            return@flow emit(Resource.Error(context.getString(R.string.no_internet_connection)))
        }

        try {
            val result = authRepository.getUser(
                token = token
            )
            return@flow emit(result)
        } catch (e: Exception) {
            return@flow emit(Resource.Error("Failed retrieve user: ${e.message}"))
        }
    }
}
