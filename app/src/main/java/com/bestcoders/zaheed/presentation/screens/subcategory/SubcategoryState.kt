package com.bestcoders.zaheed.presentation.screens.subcategory

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.bestcoders.zaheed.data.remote.model.products.PaginationMeta
import com.bestcoders.zaheed.domain.model.products.Store

data class SubcategoryState(
    val userToken: String = "",
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val error: String? = null,

    val stores: SnapshotStateList<Store> = mutableStateListOf(),
    val endReachedProducts: Boolean = false,
    val pageProducts: Int = 1,

    val userLatitude: Double = 0.0,
    val userLongitude: Double = 0.0,

    val paginationMetaProducts: PaginationMeta? = null,

    val isProductAddedToFavorite: Boolean = false,
    val isProductRemovedFromFavorite: Boolean = false,
)
