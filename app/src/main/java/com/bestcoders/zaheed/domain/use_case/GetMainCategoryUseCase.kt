package com.bestcoders.zaheed.domain.use_case

import android.content.Context
import android.util.Log
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.core.util.NetworkUtils
import com.bestcoders.zaheed.core.util.Resource
import com.bestcoders.zaheed.domain.model.products.HomeLayout
import com.bestcoders.zaheed.domain.repository.ProductsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMainCategoryUseCase @Inject constructor(
    private val productsRepository: ProductsRepository,
    @ApplicationContext private val context: Context
) {
    operator fun invoke(
        userToken: String,
        page: Int,
        categoryId: Int,
        latitude: String,
        longitude: String,
        priceRangeMax: String,
        priceRangeMin: String,
        sortBy: String,
        distance: String,
        amountOfDiscount: String
    ): Flow<Resource<HomeLayout>> = flow {
        emit(Resource.Loading())

        if (!NetworkUtils.isNetworkAvailable(context)) {
            return@flow emit(Resource.Error(context.getString(R.string.no_internet_connection)))
        }

        try {
            val result = productsRepository.getMainCategory(
                userToken = userToken.ifEmpty {
                    null
                },
                page = page,
                categoryId = categoryId,
                latitude = latitude,
                longitude = longitude,
                distance = distance,
                amountOfDiscount = amountOfDiscount,
                priceRangeMin = priceRangeMin,
                priceRangeMax = priceRangeMax,
                sortBy = sortBy,
            )
            return@flow emit(result)
        } catch (e: Exception) {
            Log.e(Constants.EXCEPTION_TAG, e.printStackTrace().toString())
        }
    }
}
