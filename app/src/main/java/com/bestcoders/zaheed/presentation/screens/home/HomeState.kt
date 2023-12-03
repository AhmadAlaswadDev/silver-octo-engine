package com.bestcoders.zaheed.presentation.screens.home

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.bestcoders.zaheed.domain.model.products.HomeLayout

data class HomeState(
    val success: Boolean = false,
    val homeLayoutList: SnapshotStateList<HomeLayout>? = null,
    val isLoading: Boolean = false,
    val isNearbyLoading: Boolean = false,
    val endReached: Boolean = false,
    val userToken: String = "",
    val userLatitude: Double = 0.0,
    val userLongitude: Double = 0.0,
    val isProductAddedToFavorite:Boolean = false,
    val isProductRemovedFromFavorite:Boolean = false,
    val error: String ?= null,

    )
