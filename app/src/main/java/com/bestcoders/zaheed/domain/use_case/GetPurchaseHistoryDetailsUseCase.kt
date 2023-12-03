package com.bestcoders.zaheed.domain.use_case

import android.content.Context
import android.util.Log
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.core.util.NetworkUtils
import com.bestcoders.zaheed.core.util.Resource
import com.bestcoders.zaheed.domain.model.track.Order
import com.bestcoders.zaheed.domain.repository.ProductsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GetPurchaseHistoryDetailsUseCase @Inject constructor(
    private val productsRepository: ProductsRepository,
    @ApplicationContext private val context: Context
) {
    operator fun invoke(
        orderId: Int,
    ): Flow<Resource<Order>> = flow {
        emit(Resource.Loading())
        if (!NetworkUtils.isNetworkAvailable(context)) {
            return@flow emit(Resource.Error(context.getString(R.string.no_internet_connection)))
        }
        try {
            val result = productsRepository.getPurchaseHistoryDetails(
                orderId = orderId,
            )
            return@flow emit(result)
        } catch (e: Exception) {
            Log.d(Constants.EXCEPTION_TAG, Log.getStackTraceString(e.cause))
        }
    }
}
