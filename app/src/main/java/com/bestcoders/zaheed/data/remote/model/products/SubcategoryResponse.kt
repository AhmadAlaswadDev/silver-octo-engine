package com.bestcoders.zaheed.data.remote.model.products

import android.os.Parcelable
import androidx.compose.runtime.toMutableStateList
import com.bestcoders.zaheed.domain.model.products.Subcategory
import kotlinx.parcelize.Parcelize

@Parcelize
data class SubcategoryResponse(
    val success: Boolean,
    val data: List<StoreWithProductsResponse>? = null,
    val pagination_meta: PaginationMetaResponse,
    val `null`: Map<String, List<String>>? = null,
) : Parcelable {

    fun toSubcategory(): Subcategory {
        return Subcategory(
            storeWithProducts = this.data?.map { it.toStore() }?.toMutableStateList(),
            paginationMeta = this.pagination_meta.toPaginationMeta()
        )
    }

}
