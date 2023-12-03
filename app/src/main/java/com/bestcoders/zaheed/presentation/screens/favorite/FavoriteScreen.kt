package com.bestcoders.zaheed.presentation.screens.favorite

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.bestcoders.zaheed.presentation.components.PaginationLoader
import com.bestcoders.zaheed.presentation.components.PrimaryProgress
import com.bestcoders.zaheed.presentation.components.PrimarySnackbar
import com.bestcoders.zaheed.presentation.components.StoreComponent
import com.bestcoders.zaheed.presentation.components.StoreWithProductsComponent
import com.bestcoders.zaheed.presentation.screens.favorite.components.FavoriteTopBar
import com.bestcoders.zaheed.presentation.screens.favorite.components.NoFavoriteItemsFoundComponent
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun FavoriteScreen(
    state: MutableState<FavoriteState>,
    onEvent: (FavoriteEvent) -> Unit,
    resetState: (FavoriteState?) -> Unit,
    uiEvent: SharedFlow<UiEvent>,
    paddingValues: PaddingValues,
    navigateToStoreDetails: (Int) -> Unit,
    navigateToProductDetails: (Int) -> Unit,
    navigateToHome: () -> Unit,
) {

    val snackbarHostState = remember { SnackbarHostState() }
    val storesProductsSelector = rememberSaveable { mutableIntStateOf(0) }
    val context = LocalContext.current
    val storesState = rememberScrollState()
    val productsState = rememberScrollState()

    LaunchedEffect(true) {
        uiEvent.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message, actionLabel = event.action
                    )
                }
            }
        }
    }
    LaunchedEffect(state.value.isProductRemovedFromFavorite) {
        if (state.value.isProductRemovedFromFavorite && state.value.error.isNullOrEmpty()) {
            onEvent(FavoriteEvent.ShowSnackBar(message = context.getString(R.string.product_removed_from_favorite)))
            resetState(state.value.copy(isProductRemovedFromFavorite = false))
            return@LaunchedEffect
        }
    }
    LaunchedEffect(state.value.isStoreRemovedFromFavorite) {
        if (state.value.isStoreRemovedFromFavorite && state.value.error.isNullOrEmpty()) {
            onEvent(FavoriteEvent.ShowSnackBar(message = context.getString(R.string.store_removed_from_favorite)))
            resetState(state.value.copy(isStoreRemovedFromFavorite = false))
            return@LaunchedEffect
        }
    }
    LaunchedEffect(state.value.error) {
        if (!state.value.error.isNullOrEmpty()) {
            onEvent(FavoriteEvent.ShowSnackBar(message = state.value.error.toString()))
            resetState(state.value.copy(error = null))
            return@LaunchedEffect
        }
    }
    LaunchedEffect(!storesState.canScrollForward) {
        if (state.value.paginationMetaStores?.nextPage != null
            && !state.value.isLoading
            && !state.value.endReachedStores
            && !storesState.canScrollForward
        ) {
            onEvent(FavoriteEvent.LoadNextItemsStores(state.value.paginationMetaStores!!.nextPage!!))
        }
    }
    LaunchedEffect(!productsState.canScrollForward) {
        if (state.value.paginationMetaProducts?.nextPage != null
            && !state.value.isLoading
            && !state.value.endReachedProducts
            && !productsState.canScrollForward
        ) {
            onEvent(FavoriteEvent.LoadNextItemsProducts(state.value.paginationMetaProducts!!.nextPage!!))
        }
    }

    if (Constants.userToken.isNotEmpty()) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                FavoriteTopBar(
                    storesProductsSelector = storesProductsSelector
                )
            },
            content = { padding ->
                // Favorite Stores
                AnimatedVisibility(
                    visible = storesProductsSelector.intValue == 0 && state.value.stores.isNotEmpty(),
                    enter = enterAnimationItems(),
                    exit = exitAnimationItems()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .imePadding()
                            .padding(
                                top = padding.calculateTopPadding(),
                                bottom = paddingValues.calculateBottomPadding(),
                            ).verticalScroll(storesState),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        // For stores
                        state.value.stores.forEach { store->
                            StoreComponent(
                                store = store,
                                onFavoriteClick = { storeId, isFavorite ->
                                    if (isFavorite) {
                                        state.value.stores.removeIf { it.id == storeId }
                                    }
                                    onEvent(
                                        FavoriteEvent.OnFavoriteStoreClick(
                                            storeId,
                                            isFavorite
                                        )
                                    )
                                },
                                onStoreClick = navigateToStoreDetails
                            )
                        }
                        if (state.value.isLoading) {
                            PaginationLoader()
                        }
                    }
                }
                // No Favorite Stores Found Component
                AnimatedVisibility(
                    visible = storesProductsSelector.intValue == 0 && state.value.stores.isEmpty() && !state.value.isLoading,
                    enter = enterFadeInAnimation(),
                    exit = exitFadeOutAnimation()
                ) {
                    NoFavoriteItemsFoundComponent(
                        favoriteType = stringResource(id = R.string.stores),
                        onStartShoppingClick = navigateToHome
                    )
                }
                // Favorite Products
                AnimatedVisibility(
                    visible = storesProductsSelector.intValue == 1 && state.value.storesWithProducts.isNotEmpty(),
                    enter = enterAnimationItems(),
                    exit = exitAnimationItems()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .imePadding()
                            .padding(
                                top = padding.calculateTopPadding(),
                                bottom = paddingValues.calculateBottomPadding(),
                            ).verticalScroll(productsState),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top,
                    ) {
                        state.value.storesWithProducts.forEach { item->
                            if (!item.products.isNullOrEmpty()) {
                                StoreWithProductsComponent(
                                    store = item,
                                    onFavoriteClick = { productId, isFavorite ->
                                        if (isFavorite) {
                                            item.products.removeIf { it.id == productId }
                                        }
                                        onEvent(
                                            FavoriteEvent.OnFavoriteProductClick(
                                                productId,
                                                isFavorite
                                            )
                                        )
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
                // No Favorite Products Found Component
                AnimatedVisibility(
                    visible = storesProductsSelector.intValue == 1 && state.value.storesWithProducts.isEmpty() && !state.value.isLoading,
                    enter = enterFadeInAnimation(),
                    exit = exitFadeOutAnimation()
                ) {
                    NoFavoriteItemsFoundComponent(
                        favoriteType = stringResource(id = R.string.products),
                        onStartShoppingClick = navigateToHome
                    )
                }
                // Loading
                AnimatedVisibility(
                    visible = state.value.isLoading && state.value.stores.isEmpty() && state.value.storesWithProducts.isEmpty(),
                    enter = enterFadeInAnimation(),
                    exit = exitFadeOutAnimation()
                ) {
                    PrimaryProgress()
                }
            }
        )
        PrimarySnackbar(snackbarHostState)
    }
}
