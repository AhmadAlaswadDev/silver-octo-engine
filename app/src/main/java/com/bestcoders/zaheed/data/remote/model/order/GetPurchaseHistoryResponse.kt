package com.bestcoders.zaheed.data.remote.model.order

import android.os.Parcelable
import com.bestcoders.zaheed.data.remote.model.order.track.OrderResponse
import com.bestcoders.zaheed.data.remote.model.products.PaginationMetaResponse
import com.bestcoders.zaheed.domain.model.products.GetPurchaseHistory
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetPurchaseHistoryResponse(
    val success: Boolean,
    val `data`: List<OrderResponse>? = null,
    val pagination_meta: PaginationMetaResponse,
    val message: String? = null,
    val errors: Map<String, List<String>>? = null,
    val status: Int,
) : Parcelable{
    fun toPurchaseHistory(): GetPurchaseHistory {
        return GetPurchaseHistory(
            orders = this.data!!.map { it.toOrder() },
            paginationMeta = this.pagination_meta.toPaginationMeta()
        )
    }
}