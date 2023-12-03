package com.bestcoders.zaheed.data.remote.model.products.cart

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartByStoreDataResponse(
    val cart: CartResponse,
    val summary: SummaryResponse
) : Parcelable