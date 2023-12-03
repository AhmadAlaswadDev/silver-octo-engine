package com.bestcoders.zaheed.domain.model.products

import com.bestcoders.zaheed.data.remote.model.products.PaginationMeta
import com.bestcoders.zaheed.domain.model.track.Order

data class GetPurchaseHistory(
    val orders: List<Order>? = null,
    val paginationMeta: PaginationMeta,
)