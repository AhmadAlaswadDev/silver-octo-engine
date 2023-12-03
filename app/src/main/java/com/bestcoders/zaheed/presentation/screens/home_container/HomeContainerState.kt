package com.bestcoders.zaheed.presentation.screens.home_container

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.bestcoders.zaheed.domain.model.products.HomeLayout

data class HomeContainerState(
    val isLoading: Boolean = false,
    val userToken: String = "",
    val success: Boolean = false,
    val userLocationSuccess: Boolean = false,
    val homeLayoutList: SnapshotStateList<HomeLayout> = mutableStateListOf(),
    val message: String = "",
    val error: String = "",
    val userLatitude: Double = 0.0,
    val userLongitude: Double = 0.0,
    val endReached: Boolean = false,
)