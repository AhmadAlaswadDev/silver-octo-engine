package com.bestcoders.zaheed.data.remote.model.products.product_details

import android.os.Parcelable
import com.bestcoders.zaheed.domain.model.products.StoreProductDetails
import kotlinx.parcelize.Parcelize

@Parcelize
data class StoreProductDetailsResponse(
    val address: String,
    val branch_name: String,
    val distance: Double,
    val id: Int,
    val logo: String,
    val name: String
) : Parcelable {
    fun toStoreProductDetails(): StoreProductDetails {
        return StoreProductDetails(
            id = this.id,
            branchName = this.branch_name,
            distance = this.distance,
            logo = this.logo,
            name = this.name,
            address = this.address,
        )
    }
}