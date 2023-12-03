package com.bestcoders.zaheed.data.remote.model.products

import android.os.Parcelable
import androidx.compose.runtime.toMutableStateList
import com.bestcoders.zaheed.domain.model.products.Favorite
import kotlinx.parcelize.Parcelize

@Parcelize
data class FavoriteResponse(
    val success: Boolean,
    val data: List<StoreWithProductsResponse>? = null,
    val pagination_meta: PaginationMetaResponse,
    val status: Int? = null,
    val message: String? = null,
    val err: Map<String, List<String>>? = null,
) : Parcelable {

    fun toFavorite(): Favorite {
        return Favorite(
            storeWithProducts = this.data?.map { it.toStore() }?.toMutableStateList(),
            paginationMeta = this.pagination_meta.toPaginationMeta()
        )
    }

}
