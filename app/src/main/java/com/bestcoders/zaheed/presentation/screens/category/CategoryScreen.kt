package com.bestcoders.zaheed.presentation.screens.category

import androidx.compose.foundation.background
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
import com.bestcoders.zaheed.navigation.Filter
import com.bestcoders.zaheed.presentation.components.AppError
import com.bestcoders.zaheed.presentation.components.NoDataFoundText
import com.bestcoders.zaheed.presentation.components.PrimaryProgress
import com.bestcoders.zaheed.presentation.components.PrimarySnackbar
import com.bestcoders.zaheed.presentation.screens.category.components.CategoryTopBar
import com.bestcoders.zaheed.presentation.screens.home.components.HomeContent
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CategoryScreen(
    state: MutableState<CategoryState>,
    uiEvent: SharedFlow<UiEvent>,
    onEvent: (CategoryEvent) -> Unit,
    resetState: (CategoryState?) -> Unit,
    navigateToFilterScreen: () -> Unit,
    navigateToSubcategoryScreen: (subcategoryId: Int, subcategoryName: String) -> Unit,
    navigateToProductDetails: (productId: Int) -> Unit,
    navigateToSelectLocation: () -> Unit,
    navigateToSignInScreen: () -> Unit,
    onNavigateBack: () -> Unit,
    onReloadClicked: () -> Unit,
    categoryName: String,
    categoryId: Int,
    filter: Filter,
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
            onEvent(CategoryEvent.ShowSnackBar(message = context.getString(R.string.product_added_to_favorite)))
            resetState(state.value.copy(isProductAddedToFavorite = false))
            return@LaunchedEffect
        }
    }

    LaunchedEffect(state.value.isProductRemovedFromFavorite) {
        if (state.value.isProductRemovedFromFavorite && state.value.error.isNullOrEmpty()) {
            onEvent(CategoryEvent.ShowSnackBar(message = context.getString(R.string.product_removed_from_favorite)))
            resetState(state.value.copy(isProductRemovedFromFavorite = false))
            return@LaunchedEffect
        }
    }

    LaunchedEffect(state.value.error) {
        if (!state.value.error.isNullOrEmpty()) {
            onEvent(CategoryEvent.ShowSnackBar(message = state.value.error.toString()))
            resetState(state.value.copy(error = null))
            return@LaunchedEffect
        }
    }

    Scaffold(
        modifier = Modifier.background(Color.Transparent),
        topBar = {
            CategoryTopBar(
                label = categoryName,
                onFilterClicked = navigateToFilterScreen,
                onBackClicked = onNavigateBack
            )
        },
        content = { padding ->
            if (state.value.homeLayoutList.isNotEmpty() && !state.value.isLoading) {
                HomeContent(
                    homeLayoutList = state.value.homeLayoutList,
                    padding = padding,
                    homePadding = padding,
                    loadNextItems = { page ->
                        onEvent(
                            CategoryEvent.LoadNextItems(
                                categoryId = categoryId,
                                page = page,
                                amountOfDiscount = filter.amountOfDiscount,
                                sortBy = filter.sortBy,
                                priceRangeMin = filter.priceRangeMin,
                                priceRangeMax = filter.priceRangeMax,
                            )
                        )
                    },
                    isNearbyLoading = state.value.isNearbyLoading,
                    nearbyEndReached = state.value.endReached,
                    onFavoriteClick = { productId, isFavorite ->
                        if (Constants.userToken.isNotEmpty()) {
                            onEvent(CategoryEvent.OnFavoriteProductClick(productId, isFavorite))
                        } else {
                            onEvent(
                                CategoryEvent.ShowSnackBar(
                                    message = context.getString(R.string.you_should_signin_to_continue),
                                    action = context.getString(R.string.sign_in)
                                )
                            )
                        }
                    },
                    onCategoryClick = navigateToSubcategoryScreen,
                    onProductClick = navigateToProductDetails,
                    onStoreClick = navigateToStoreDetails
                )
            } else if (state.value.homeLayoutList.isEmpty() && state.value.error.isNullOrEmpty() && !state.value.isLoading) {
                NoDataFoundText()
            } else if (!state.value.error.isNullOrEmpty() && !state.value.isLoading) {
                AppError(error = state.value.error!!, onReloadClicked = onReloadClicked)
            } else {
                PrimaryProgress()
            }
        },
    )
    PrimarySnackbar(snackbarHostState)
}







