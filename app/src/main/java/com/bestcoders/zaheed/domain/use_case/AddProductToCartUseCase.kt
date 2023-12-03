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
import com.bestcoders.zaheed.domain.repository.ProductsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddProductToCartUseCase @Inject constructor(
    private val productsRepository: ProductsRepository,
    private var dataStore: DataStore<UserAuthDataStoreEntity>,
    @ApplicationContext private val context: Context
) {
    operator fun invoke(
        productId: Int,
        variant: String,
        quantity: Int
    ): Flow<Resource<String>> = flow {
        emit(Resource.Loading())

        if (!NetworkUtils.isNetworkAvailable(context)) {
            return@flow emit(Resource.Error(context.getString(R.string.no_internet_connection)))
        }

        try {
            val result = productsRepository.addProductToCart(
                productId = productId,
                variant = variant,
                quantity = quantity,
            )
            if (!result.data.isNullOrEmpty()) {
                UserAuthDataStoreManager.setUserAuthData(
                    dataStore = dataStore,
                    userAuthDataStoreEntity = UserAuthDataStoreEntity(tempToken = result.data)
                )
                Constants.tempToken = result.data
            }
            return@flow emit(result)
        } catch (e: Exception) {
            Log.e(Constants.EXCEPTION_TAG, e.printStackTrace().toString())
        }
    }
}
