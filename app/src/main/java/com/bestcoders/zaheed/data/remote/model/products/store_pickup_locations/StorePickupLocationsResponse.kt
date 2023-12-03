package com.bestcoders.zaheed.data.remote.model.products.store_pickup_locations

import android.os.Parcelable
import com.bestcoders.zaheed.domain.model.track.PickupPoint
import kotlinx.parcelize.Parcelize

@Parcelize
data class StorePickupLocationsResponse(
    val success: Boolean,
    val `data`: List<StorePickupLocationsDataResponse>? = null,
    val message: String? = null,
    val err: Map<String, List<String>>? = null
) : Parcelable {

    @Parcelize
    data class StorePickupLocationsDataResponse(
        val id: Int,
        val address: String,
        val latitude: String,
        val longitude: String,
        val phone: String,
        val shop: String
    ) : Parcelable {

        fun toStorePickupLocation(): PickupPoint {
            return PickupPoint(
                id = this.id,
                address = this.address,
                latitude = this.latitude,
                longitude = this.longitude,
                phone = this.phone,
                store = this.shop
            )
        }

    }


}