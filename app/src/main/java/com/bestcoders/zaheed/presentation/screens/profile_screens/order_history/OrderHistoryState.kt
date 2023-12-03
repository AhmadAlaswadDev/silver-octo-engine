package com.bestcoders.zaheed.presentation.screens.profile_screens.order_history

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.bestcoders.zaheed.data.remote.model.products.PaginationMeta
import com.bestcoders.zaheed.domain.model.track.Order

data class OrderHistoryState(
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val error: String? = null,
    val paginationMeta: PaginationMeta? = null,
    val orders: SnapshotStateList<Order> = mutableStateListOf(),
)
