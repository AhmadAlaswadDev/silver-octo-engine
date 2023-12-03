package com.bestcoders.zaheed.presentation.screens.category

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.bestcoders.zaheed.domain.model.products.HomeLayout
import com.bestcoders.zaheed.presentation.screens.home_container.HomeContainerViewModel

data class CategoryState(
    val userToken: String = "",
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val homeLayoutList: SnapshotStateList<HomeLayout> = mutableStateListOf(),
    val isNearbyLoading: Boolean = false,
    val message: String = "",
    val error: String? = null,
    val userLatitude: Double = 0.0,
    val userLongitude: Double = 0.0,
    val endReached: Boolean = false,
    val isProductAddedToFavorite: Boolean = false,
    val isProductRemovedFromFavorite: Boolean = false,
    val isStoreAddedToFavorite: Boolean = false,
    val isStoreRemovedFromFavorite: Boolean = false,

    )

