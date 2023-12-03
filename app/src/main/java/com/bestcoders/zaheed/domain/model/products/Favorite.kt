package com.bestcoders.zaheed.domain.model.products

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.bestcoders.zaheed.data.remote.model.products.PaginationMeta

data class Favorite(
    val storeWithProducts: SnapshotStateList<Store>?,
    val paginationMeta: PaginationMeta,
)
