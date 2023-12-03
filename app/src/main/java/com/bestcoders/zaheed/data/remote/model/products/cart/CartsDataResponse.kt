package com.bestcoders.zaheed.data.remote.model.products.cart

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartsDataResponse(
    val carts: List<CartResponse>? = null,
    val summary: SummaryResponse? = null
) : Parcelable