package com.bestcoders.zaheed.domain.model.products

import com.bestcoders.zaheed.data.remote.model.products.PaginationMeta

data class AllProductsStoreDetails(
    val items: List<Product>,
    val paginationMeta: PaginationMeta,
)

