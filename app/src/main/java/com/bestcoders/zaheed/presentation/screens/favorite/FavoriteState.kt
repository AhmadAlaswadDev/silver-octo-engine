package com.bestcoders.zaheed.presentation.screens.favorite

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.bestcoders.zaheed.data.remote.model.products.PaginationMeta
import com.bestcoders.zaheed.domain.model.products.Store

data class FavoriteState(
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val error: String? = null,

    val storesWithProducts: SnapshotStateList<Store> = mutableStateListOf(),
    val stores: SnapshotStateList<Store> = mutableStateListOf(),
    val endReachedProducts: Boolean = false,
    val endReachedStores: Boolean = false,
    val pageProducts: Int = 1,
    val pageStores: Int = 1,

    val userLatitude: Double = 0.0,
    val userLongitude: Double = 0.0,

    val paginationMetaProducts: PaginationMeta? = null,
    val paginationMetaStores: PaginationMeta? = null,

    val isProductAddedToFavorite: Boolean = false,
    val isProductRemovedFromFavorite: Boolean = false,
    val isStoreAddedToFavorite: Boolean = false,
    val isStoreRemovedFromFavorite: Boolean = false,
)
