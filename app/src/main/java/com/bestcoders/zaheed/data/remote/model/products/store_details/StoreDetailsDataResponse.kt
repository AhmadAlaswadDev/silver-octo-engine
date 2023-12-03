package com.bestcoders.zaheed.data.remote.model.products.store_details

import android.os.Parcelable
import com.bestcoders.zaheed.data.remote.model.products.ShopResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class StoreDetailsDataResponse(
        val id: Int,
        val address: String,
        val branch_name: String,
        val distance: Double,
        val is_favorite: Boolean,
        val is_subscribed: Boolean,
        val latitude: String,
        val logo: String,
        val longitude: String,
        val name: String,
        val refund_policy: String,
        val saved: Double,
        val sliders: List<String>,
        val sections: List<StoreSectionResponse>,
        val all: AllProductsStoreDetailsResponse,
        val shop_category: ShopResponse,
        val working_hours: List<WorkingHoursResponse>
): Parcelable

