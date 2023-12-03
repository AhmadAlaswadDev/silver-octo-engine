package com.bestcoders.zaheed.presentation.screens.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.bestcoders.zaheed.presentation.screens.search.components.NoSearchResultFound
import com.bestcoders.zaheed.presentation.screens.search.components.ResultsCount
import com.bestcoders.zaheed.presentation.screens.search.components.SearchTopBar
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchScreen(
    state: MutableState<SearchState>,
    onEvent: (SearchEvent) -> Unit,
    onNavigateBack: () -> Unit,
    navigateToProductDetails: (Int) -> Unit,
    navigateToStoreDetails: (Int) -> Unit,
    resetState: (SearchState) -> Unit,
    uiEvent: SharedFlow<UiEvent>,
    navigateToSignInScreen: () -> Unit,
    navigateToHome: () -> Unit,
) {

    val snackbarHostState = remember { SnackbarHostState() }
    val storesProductsSelector = remember { mutableStateOf(0) }
    val context = LocalContext.current
    val storesState = rememberScrollState()
    val productsState = rememberScrollState()
    var searchValue by rememberSaveable { mutableStateOf("") }
    val scope = rememberCoroutineScope()

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
            onEvent(SearchEvent.ShowSnackBar(message = context.getString(R.string.product_added_to_favorite)))
            resetState(state.value.copy(isProductAddedToFavorite = false))
            return@LaunchedEffect
        }
    }
    LaunchedEffect(state.value.isStoreAddedToFavorite) {
        if (state.value.isStoreAddedToFavorite && state.value.error.isNullOrEmpty()) {
            onEvent(SearchEvent.ShowSnackBar(message = context.getString(R.string.store_added_to_favorite)))
            resetState(state.value.copy(isStoreAddedToFavorite = false))
            return@LaunchedEffect
        }
    }
    LaunchedEffect(state.value.isProductRemovedFromFavorite) {
        if (state.value.isProductRemovedFromFavorite && state.value.error.isNullOrEmpty()) {
            onEvent(SearchEvent.ShowSnackBar(message = context.getString(R.string.product_removed_from_favorite)))
            resetState(state.value.copy(isProductRemovedFromFavorite = false))
            return@LaunchedEffect
        }
    }
    LaunchedEffect(state.value.isStoreRemovedFromFavorite) {
        if (state.value.isStoreRemovedFromFavorite && state.value.error.isNullOrEmpty()) {
            onEvent(SearchEvent.ShowSnackBar(message = context.getString(R.string.store_removed_from_favorite)))
            resetState(state.value.copy(isStoreRemovedFromFavorite = false))
            return@LaunchedEffect
        }
    }
    LaunchedEffect(state.value.error) {
        if (!state.value.error.isNullOrEmpty()) {
            onEvent(SearchEvent.ShowSnackBar(message = state.value.error.toString()))
            resetState(state.value.copy(error = null))
            return@LaunchedEffect
        }
    }
    LaunchedEffect(!storesState.canScrollForward) {
        if (state.value.paginationMetaStores?.nextPage != null
            && !state.value.isLoading
            && !state.value.endReachedStores
            && !storesState.canScrollForward
            && searchValue.isNotEmpty()
        ) {
            onEvent(
                SearchEvent.LoadNextItemsStores(
                    page = state.value.paginationMetaStores!!.nextPage!!,
                    storeName = searchValue
                )
            )
        }
    }
    LaunchedEffect(!productsState.canScrollForward) {
        if (state.value.paginationMetaProducts?.nextPage != null
            && !state.value.isLoading
            && !state.value.endReachedProducts
            && !productsState.canScrollForward
            && searchValue.isNotEmpty()
        ) {
            onEvent(
                SearchEvent.LoadNextItemsProducts(
                    page = state.value.paginationMetaProducts!!.nextPage!!,
                    productName = searchValue
                )
            )
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SearchTopBar(
                onSearchValueChange = { value ->
                    searchValue = value
                    if (value.isNotEmpty()) {
                        scope.launch {
                            // Animate scroll to the 10th item
                            storesState.scrollTo(0)
                            productsState.scrollTo(0)
                        }
                        resetState(
                            state.value.copy(
                                stores = mutableStateListOf(),
                                storesWithProducts = mutableStateListOf(),
                                endReachedStores = false,
                                endReachedProducts = false,
                                pageStores = 1,
                                pageProducts = 1
                            )
                        )
                        onEvent(
                            SearchEvent.GetSearchedStores(
                                page = state.value.paginationMetaStores?.nextPage ?: 1,
                                storeName = value
                            )
                        )
                        onEvent(
                            SearchEvent.GetSearchedProducts(
                                page = state.value.paginationMetaProducts?.nextPage ?: 1,
                                productName = value
                            )
                        )
                    }
                },
                onCancelClick = onNavigateBack,
                storesProductsSelector = storesProductsSelector,
            )
        },
        content = { padding ->
            // Stores
            AnimatedVisibility(
                visible = storesProductsSelector.value == 0 && state.value.stores.isNotEmpty() && searchValue.isNotEmpty(),
                enter = enterAnimationItems(),
                exit = exitAnimationItems()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .imePadding()
                        .padding(
                            top = padding.calculateTopPadding(),
                        )
                        .verticalScroll(storesState),
                ) {
                    // For stores
                    ResultsCount(
                        results = state.value.paginationMetaStores?.totalItems ?: "0"
                    )
                    state.value.stores.forEach { store ->
                        StoreComponent(
                            store = store,
                            onStoreClick = navigateToStoreDetails,
                            onFavoriteClick = { storeId, isFavorite ->
                                if (Constants.userToken.isNotEmpty()) {
                                    onEvent(
                                        SearchEvent.OnFavoriteStoreClick(
                                            storeId,
                                            isFavorite
                                        )
                                    )
                                } else {
                                    onEvent(
                                        SearchEvent.ShowSnackBar(
                                            message = context.getString(
                                                R.string.you_should_signin_to_continue
                                            )
                                        )
                                    )
                                }
                            }
                        )
                    }
                    if (state.value.isLoading) {
                        PaginationLoader()
                    }
                }
            }
            // No Stores Found Component
            AnimatedVisibility(
                visible = storesProductsSelector.value == 0 && (state.value.stores.isEmpty() || searchValue.isEmpty()) && !state.value.isLoading,
                enter = enterFadeInAnimation(),
                exit = exitFadeOutAnimation()
            ) {
                NoSearchResultFound(onStartShoppingClick = navigateToHome)
            }
            // Products
            AnimatedVisibility(
                visible = storesProductsSelector.value == 1 && state.value.storesWithProducts.isNotEmpty() && searchValue.isNotEmpty(),
                enter = enterAnimationItems(),
                exit = exitAnimationItems()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .imePadding()
                        .padding(
                            top = padding.calculateTopPadding(),
                        )
                        .verticalScroll(productsState),
                ) {
                    ResultsCount(
                        results = state.value.paginationMetaProducts?.totalItems ?: "0"
                    )
                    state.value.storesWithProducts.forEach { item ->
                        StoreWithProductsComponent(
                            store = item,
                            onProductClick = navigateToProductDetails,
                            onFavoriteClick = { productId, isFavorite ->
                                if (Constants.userToken.isNotEmpty()) {
                                    onEvent(
                                        SearchEvent.OnFavoriteProductClick(
                                            productId,
                                            isFavorite
                                        )
                                    )
                                } else {
                                    onEvent(
                                        SearchEvent.ShowSnackBar(
                                            message = context.getString(
                                                R.string.you_should_signin_to_continue
                                            )
                                        )
                                    )
                                }
                            },
                            onStoreClick = navigateToStoreDetails
                        )
                    }
                    if (state.value.isLoading) {
                        PaginationLoader()
                    }
                }
            }
            // No Products Found Component
            AnimatedVisibility(
                visible = storesProductsSelector.value == 1 && (state.value.storesWithProducts.isEmpty() || searchValue.isEmpty()) && !state.value.isLoading,
                enter = enterFadeInAnimation(),
                exit = exitFadeOutAnimation()
            ) {
                NoSearchResultFound(onStartShoppingClick = navigateToHome)
            }
            // Loading
            AnimatedVisibility(
                visible = state.value.isLoading && state.value.stores.isEmpty() && state.value.storesWithProducts.isEmpty(),
                enter = enterFadeInAnimation(),
                exit = exitFadeOutAnimation()
            ) {
                PrimaryProgress()
            }


            PrimarySnackbar(snackbarHostState)
        }
    )
}



