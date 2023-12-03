package com.bestcoders.zaheed.data.remote.model.products.store_details

import android.os.Parcelable
import com.bestcoders.zaheed.domain.model.products.StoreDetails
import kotlinx.parcelize.Parcelize

@Parcelize
data class StoreDetailsResponse(
        val success: Boolean,
        val `data`: StoreDetailsDataResponse,
        val message: String? = null,
        val status: Int,
) : Parcelable {

    fun toStoreDetails(): StoreDetails {
        return StoreDetails(
                id = this.data.id,
                address = this.data.address,
                branchName = this.data.branch_name,
                distance = this.data.distance,
                isFavorite = this.data.is_favorite,
                isSubscribed = this.data.is_subscribed,
                latitude = this.data.latitude,
                longitude = this.data.longitude,
                logo = this.data.logo,
                name = this.data.name,
                refundPolicy = this.data.refund_policy,
                saved = this.data.saved,
                sliders = this.data.sliders,
                sections = this.data.sections.map { it.toStoreSection() },
                category = this.data.shop_category.toShopCategory(),
                workingHours = this.data.working_hours.map { it.toWorkingHours() },
                all = this.data.all.toAllProductsStoreDetails()
        )
    }

}
