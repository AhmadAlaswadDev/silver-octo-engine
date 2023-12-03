package com.bestcoders.zaheed.data.remote.model.settings

import android.os.Parcelable
import com.bestcoders.zaheed.domain.model.settings.OrderPaymentStatus
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderPaymentStatusResponse(
    val name: String,
    val value: String,
) : Parcelable {
    fun toOrderPaymentStatus(): OrderPaymentStatus {
        return OrderPaymentStatus(
            name = this.name,
            value = this.value,
        )
    }
}