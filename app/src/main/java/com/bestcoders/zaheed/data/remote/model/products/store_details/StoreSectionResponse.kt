package com.bestcoders.zaheed.data.remote.model.products.store_details

import android.os.Parcelable
import com.bestcoders.zaheed.domain.model.products.StoreSection
import kotlinx.parcelize.Parcelize

@Parcelize
data class StoreSectionResponse(
    val section_id: Int,
    val section_name: String,
    val products_count: Int,
    val products: List<ProductStoreDetailsResponse>
) : Parcelable {

    fun toStoreSection(): StoreSection {
        return StoreSection(
            sectionId = this.section_id,
            sectionName = this.section_name,
            productsCount = this.products_count,
            products = this.products.map { it.toProductStoreDetails() }.toMutableList(),
        )
    }

}