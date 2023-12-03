package com.bestcoders.zaheed.data.remote.model.products.cart

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddProductToCartResponse(
    val success: Boolean,
    val temp_user: String? = null,
    val message: String,
    val err: Map<String, List<String>>? = null,
) : Parcelable