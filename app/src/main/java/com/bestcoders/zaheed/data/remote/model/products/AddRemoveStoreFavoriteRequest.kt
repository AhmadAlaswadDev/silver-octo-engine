package com.bestcoders.zaheed.data.remote.model.products

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddRemoveStoreFavoriteRequest(
    @SerializedName("shop_id") val storeId : Int
): Parcelable
