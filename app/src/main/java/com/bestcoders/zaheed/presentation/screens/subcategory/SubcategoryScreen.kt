package com.bestcoders.zaheed.presentation.screens.subcategory

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.extentions.enterAnimationItems
import com.bestcoders.zaheed.core.extentions.enterFadeInAnimation
import com.bestcoders.zaheed.core.extentions.exitAnimationItems
import com.bestcoders.zaheed.core.extentions.exitFadeOutAnimation
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.core.util.UiEvent
import com.bestcoders.zaheed.presentation.components.AppError
import com.bestcoders.zaheed.presentation.components.NoDataFoundText
import com.bestcoders.zaheed.presentation.components.PaginationLoader
import com.bestcoders.zaheed.presentation.components.PrimaryProgress
import com.bestcoders.zaheed.presentation.components.PrimarySnackbar
import com.bestcoders.zaheed.presentation.components.StoreWithProductsComponent
import com.bestcoders.zaheed.presentation.screens.favorite.components.NoFavoriteItemsFoundComponent
import com.bestcoders.zaheed.presentation.screens.home.components.NotSupportedLocation
import com.bestcoders.zaheed.presentation.screens.store_details.StoreDetailsEvent
import com.bestcoders.zaheed.presentation.screens.subcategory.components.SubcategoryTopBar
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SubcategoryScreen(
    state: MutableState<SubcategoryState>,
    onEvent: (SubcategoryEvent) -> Unit,
    navigateToFilterScreen: () -> Unit,
    onNavigateBack: () -> Unit,
    resetState: (SubcategoryState) -> Unit,
    uiEvent: SharedFlow<UiEvent>,
    subcategoryId: Int,
    subcategoryName: String,
    navigateToProductDetails: (productId: Int) -> Unit,
    navigateToStoreDetails: (storeId: Int) -> Unit,
    navigateToSelectLocation: () -> Unit,
    navigateToSignInScreen: () -> Unit,
    onReloadClicked: () -> Unit,
) {

    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val listState = rememberScrollState()

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
            onEvent(SubcategoryEvent.ShowSnackBar(message = context.getString(R.string.product_added_to_favorite)))
            resetState(state.value.copy(isProductAddedToFavorite = false))
            return@LaunchedEffect
        }
    }
    LaunchedEffect(state.value.isProductRemovedFromFavorite) {
        if (state.value.isProductRemovedFromFavorite && state.value.error.isNullOrEmpty()) {
            onEvent(SubcategoryEvent.ShowSnackBar(message = context.getString(R.string.product_removed_from_favorite)))
            resetState(state.value.copy(isProductRemovedFromFavorite = false))
            return@LaunchedEffect
        }
    }
    LaunchedEffect(state.value.error) {
        if (!state.value.error.isNullOrEmpty()) {
            onEvent(SubcategoryEvent.ShowSnackBar(message = state.value.error.toString()))
            resetState(state.value.copy(error = null))
            return@LaunchedEffect
        }
    }
    LaunchedEffect(!listState.canScrollForward) {
        if (state.value.paginationMetaProducts != null
            && state.value.paginationMetaProducts?.nextPage != null
            && !state.value.isLoading
            && !state.value.endReachedProducts
            && !listState.canScrollForward
        ) {
            onEvent(
                SubcategoryEvent.LoadNextItems(
                    subcategoryId = subcategoryId,
                    page = state.value.paginationMetaProducts!!.nextPage!!
                )
            )
        }
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SubcategoryTopBar(
                label = subcategoryName,
                onFilterClicked = navigateToFilterScreen,
                onBackClicked = onNavigateBack
            )
        },
        content = { padding ->
            // Stores
            AnimatedVisibility(
                visible = state.value.stores.isNotEmpty() && !state.value.isLoading,
                enter = enterAnimationItems(),
                exit = exitAnimationItems()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .imePadding()
                        .verticalScroll(listState)
                        .padding(padding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    state.value.stores.forEach { store ->
                        if (!store.products.isNullOrEmpty()) {
                            StoreWithProductsComponent(
                                store = store,
                                onFavoriteClick = { productId, isFavorite ->
                                    if (Constants.userToken.isNotEmpty()) {
                                        onEvent(
                                            SubcategoryEvent.OnFavoriteProductClick(
                                                productId,
                                                isFavorite
                                            )
                                        )
                                    } else {
                                        onEvent(
                                            SubcategoryEvent.ShowSnackBar(
                                                message = context.getString(
                                                    R.string.you_should_signin_to_continue
                                                )
                                            )
                                        )
                                    }
                                },
                                onProductClick = navigateToProductDetails,
                                onStoreClick = navigateToStoreDetails
                            )
                        }
                    }
                    if (state.value.isLoading) {
                        PaginationLoader()
                    }
                }
            }
            // Not Supported Location
            AnimatedVisibility(
                visible = state.value.stores.isEmpty() && state.value.error.isNullOrEmpty() && !state.value.isLoading,
                enter = enterFadeInAnimation(),
                exit = exitFadeOutAnimation()
            ) {
                NotSupportedLocation(onClick = navigateToSelectLocation)
            }
            // Error
            AnimatedVisibility(
                visible = !state.value.error.isNullOrEmpty() && !state.value.isLoading,
                enter = enterFadeInAnimation(),
                exit = exitFadeOutAnimation()
            ) {
                AppError(error = state.value.error!!, onReloadClicked = onReloadClicked)
            }
            // Loading
            AnimatedVisibility(
                visible = state.value.isLoading && state.value.stores.isEmpty(),
                enter = enterFadeInAnimation(),
                exit = exitFadeOutAnimation()
            ) {
                PrimaryProgress()
            }
        }
    )
    PrimarySnackbar(snackbarHostState)

}

