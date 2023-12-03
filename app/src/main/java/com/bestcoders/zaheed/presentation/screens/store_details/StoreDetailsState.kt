package com.bestcoders.zaheed.presentation.screens.store_details

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.bestcoders.zaheed.data.remote.model.products.PaginationMeta
import com.bestcoders.zaheed.domain.model.products.Product
import com.bestcoders.zaheed.domain.model.products.StoreDetails

data class StoreDetailsState(
    val isLoading: Boolean = false,
    val followStoreSuccess: Boolean = false,
    val unfollowStoreSuccess: Boolean = false,
    val getStoreDetailsSuccess: Boolean = false,
    val error: String? = null,
    val storeDetails: StoreDetails? = null,
    val userLatitude: Double = 0.0,
    val userLongitude: Double = 0.0,
    val isStoreAddedToFavorite: Boolean = false,
    val isStoreRemovedFromFavorite: Boolean = false,
    val allProducts: SnapshotStateList<Product> = mutableStateListOf(),
    val paginationMeta: PaginationMeta? = null,
)
