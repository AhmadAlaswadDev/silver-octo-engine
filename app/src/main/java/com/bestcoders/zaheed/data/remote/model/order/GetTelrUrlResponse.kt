package com.bestcoders.zaheed.data.remote.model.order

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetTelrUrlResponse(
    val success: Boolean,
    val `data`: GetTelrUrlDataResponse? = null,
    val status: Int,
    val message: String? = null,
    val errors: Map<String, List<String>>? = null,
) : Parcelable {

    @Parcelize
    data class GetTelrUrlDataResponse(
        val redirect_url: String
    ) : Parcelable

}