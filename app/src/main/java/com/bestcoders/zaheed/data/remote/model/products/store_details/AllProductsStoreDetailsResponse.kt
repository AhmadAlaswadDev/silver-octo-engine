package com.bestcoders.zaheed.data.remote.model.products.store_details

import android.os.Parcelable
import com.bestcoders.zaheed.data.remote.model.products.PaginationMetaResponse
import com.bestcoders.zaheed.domain.model.products.AllProductsStoreDetails
import kotlinx.parcelize.Parcelize

@Parcelize
data class AllProductsStoreDetailsResponse(
    val items: List<ProductStoreDetailsResponse>,
    val pagination_meta: PaginationMetaResponse,
) : Parcelable {
    fun toAllProductsStoreDetails(): AllProductsStoreDetails {
        return AllProductsStoreDetails(
            items = this.items.map { it.toProductStoreDetails() },
            paginationMeta = this.pagination_meta.toPaginationMeta()
        )
    }
}

