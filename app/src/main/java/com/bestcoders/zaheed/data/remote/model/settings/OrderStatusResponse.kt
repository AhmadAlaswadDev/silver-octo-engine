package com.bestcoders.zaheed.data.remote.model.settings

import android.os.Parcelable
import com.bestcoders.zaheed.domain.model.settings.OrderStatus
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderStatusResponse(
    val name: String,
    val value: String,
    val color: String,
) : Parcelable {
    fun toOrderStatus(): OrderStatus {
        return OrderStatus(
            name = this.name,
            value = this.value,
            color = this.color,
        )
    }
}