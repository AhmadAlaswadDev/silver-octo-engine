package com.bestcoders.zaheed.data.remote.model.products

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddRemoveProductFavoriteRequest(
    @SerializedName("product_id") val productId : String
): Parcelable
