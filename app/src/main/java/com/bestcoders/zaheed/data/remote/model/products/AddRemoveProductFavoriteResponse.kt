package com.bestcoders.zaheed.data.remote.model.products

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddRemoveProductFavoriteResponse(
    val success: Boolean,
    val message:String,
    val errors: Map<String, List<String>>? = null,
) : Parcelable