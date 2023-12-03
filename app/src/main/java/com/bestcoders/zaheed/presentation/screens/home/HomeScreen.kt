package com.bestcoders.zaheed.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.core.util.UiEvent
import com.bestcoders.zaheed.presentation.components.AppError
import com.bestcoders.zaheed.presentation.components.GpsScreen
import com.bestcoders.zaheed.presentation.components.LocationPermissionAlert
import com.bestcoders.zaheed.presentation.components.PrimaryProgress
import com.bestcoders.zaheed.presentation.components.PrimarySnackbar
import com.bestcoders.zaheed.presentation.screens.home.components.HomeContent
import com.bestcoders.zaheed.presentation.screens.home.components.HomeTopBar
import com.bestcoders.zaheed.presentation.screens.home.components.NotSupportedLocation
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest


@Composable
fun HomeScreen(
    homePadding: PaddingValues,
    state: MutableState<HomeState>,
    uiEvent: SharedFlow<UiEvent>,
    onEvent: (HomeEvent) -> Unit,
    resetState: (HomeState?) -> Unit,
    loadNextItems: (nextPage: Int) -> Unit,
    onReloadClicked: () -> Unit,
    navigateToSearchScreen: () -> Unit,
    navigateToFilterScreen: () -> Unit,
    navigateToCategoryScreen: (categoryId: Int, subcategoryName: String) -> Unit,
    navigateToProductDetails: (productId: Int) -> Unit,
    navigateToSignInScreen: () -> Unit,
    navigateToMapScreen: () -> Unit,
    requestLocationPermission: () -> Unit,
    permissionGranted: MutableState<Boolean>,
    navigateToStoreDetails: (Int) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current



    LaunchedEffect(true) {
        uiEvent.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    when (
                        snackbarHostState.showSnackbar(
                            message = event.message,
                            actionLabel = event.action,
                        )
                    ) {
                        SnackbarResult.Dismissed -> {}
                        SnackbarResult.ActionPerformed -> {
                            navigateToSignInScreen()
                        }
                    }
                }
            }
        }
    }



    LaunchedEffect(state.value.isProductAddedToFavorite) {
        if (state.value.isProductAddedToFavorite && state.value.error.isNullOrEmpty()) {
            onEvent(HomeEvent.ShowSnackBar(message = context.getString(R.string.product_added_to_favorite)))
            resetState(null)
            return@LaunchedEffect
        }
    }

    LaunchedEffect(state.value.isProductRemovedFromFavorite) {
        if (state.value.isProductRemovedFromFavorite && state.value.error.isNullOrEmpty()) {
            onEvent(HomeEvent.ShowSnackBar(message = context.getString(R.string.product_removed_from_favorite)))
            resetState(null)
            return@LaunchedEffect
        }
    }

    LaunchedEffect(state.value.error) {
        if (!state.value.error.isNullOrEmpty()) {
            onEvent(HomeEvent.ShowSnackBar(message = state.value.error.toString()))
            resetState(null)
            return@LaunchedEffect
        }
    }

    Scaffold(
        modifier = Modifier.background(Color.Transparent),
        topBar = {
            HomeTopBar(
                savedMoneyLabel = Constants.userSaved,
                onLocationIconClick = navigateToMapScreen,
                navigateToSearchScreen = navigateToSearchScreen,
                onFilterClick = navigateToFilterScreen,
            )
        },
        content = { padding ->
            LocationPermissionAlert(
                permissionGranted = permissionGranted,
                requestLocationPermission = { requestLocationPermission() },
                onPermissionGranted = {
                    GpsScreen {
                        if (permissionGranted.value && !state.value.homeLayoutList.isNullOrEmpty()) {
                            HomeContent(
                                homeLayoutList = state.value.homeLayoutList,
                                padding = padding,
                                homePadding = homePadding,
                                loadNextItems = loadNextItems,
                                isNearbyLoading = state.value.isNearbyLoading,
                                nearbyEndReached = state.value.endReached,
                                onFavoriteClick = { productId, isFavorite ->
                                    if (Constants.userToken.isNotEmpty()) {
                                        onEvent(HomeEvent.OnFavoriteClick(productId, isFavorite))
                                    } else {
                                        onEvent(
                                            HomeEvent.ShowSnackBar(
                                                message = context.getString(R.string.you_should_signin_to_continue),
                                                action = context.getString(R.string.sign_in)
                                            )
                                        )
                                    }
                                },
                                onCategoryClick = navigateToCategoryScreen,
                                onProductClick = navigateToProductDetails,
                                onStoreClick= navigateToStoreDetails,
                            )
                        } else if (state.value.homeLayoutList.isNullOrEmpty()) {
                            NotSupportedLocation(
                                onClick = navigateToMapScreen
                            )
                        } else if (!state.value.error.isNullOrEmpty()) {
                            AppError(error = state.value.error!!, onReloadClicked = onReloadClicked)
                        }else if(state.value.homeLayoutList.isNullOrEmpty() && state.value.isLoading){
                            PrimaryProgress()
                        }else{
                            PrimaryProgress()
                        }
                    }
                }
            )
        },
    )

    PrimarySnackbar(snackbarHostState)

}












