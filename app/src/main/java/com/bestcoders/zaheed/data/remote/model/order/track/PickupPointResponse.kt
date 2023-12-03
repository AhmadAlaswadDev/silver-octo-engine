package com.bestcoders.zaheed.data.remote.model.order.track

import android.os.Parcelable
import com.bestcoders.zaheed.data.remote.model.products.store_details.WorkingHoursResponse
import com.bestcoders.zaheed.domain.model.track.PickupPoint
import kotlinx.parcelize.Parcelize

@Parcelize
data class PickupPointResponse(
    val address: String,
    val id: Int,
    val latitude: String,
    val longitude: String,
    val phone: String,
    val shop: String?,
    val open_days: List<WorkingHoursResponse>?,
) : Parcelable {
    fun toPickupPoint(): PickupPoint {
        return PickupPoint(
            address = this.address,
            id = this.id,
            latitude = this.latitude,
            longitude = this.longitude,
            phone = this.phone,
            store = this.shop,
            openDays = this.open_days?.map { it.toWorkingHours() },
        )
    }
}