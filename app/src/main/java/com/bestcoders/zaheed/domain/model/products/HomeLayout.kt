package com.bestcoders.zaheed.domain.model.products

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.bestcoders.zaheed.data.remote.model.products.PaginationMeta

data class HomeLayout(
    val bestSales: SnapshotStateList<BestSale>? = null,
    val categories: SnapshotStateList<Category>? = null,
    val homeBanner: SnapshotStateList<HomeBanner>? = null,
    val nearbyStores: SnapshotStateList<Store>? = null,
    val paginationMeta: PaginationMeta? = null,
)