package com.bestcoders.zaheed.data.remote.model.auth

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VerifyCodeRegisterResponse(
    val success: Boolean,
    val message: String,
    val data: VerifyCodeRegisterResponseData? = null,
    val err: Map<String, List<String>>? = null,
) : Parcelable {

    @Parcelize
    data class VerifyCodeRegisterResponseData(
        val registration_request_id: Int? = null
    ) : Parcelable

}