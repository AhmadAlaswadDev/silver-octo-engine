package com.bestcoders.zaheed.presentation.screens.checkout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.util.UiEvent
import com.bestcoders.zaheed.domain.model.products.Product
import com.bestcoders.zaheed.presentation.components.CheckoutAndConfirmOrderScreenFooter
import com.bestcoders.zaheed.presentation.components.PrimaryProgress
import com.bestcoders.zaheed.presentation.components.PrimarySnackbar
import com.bestcoders.zaheed.presentation.components.PrimaryTopBarBigTitle
import com.bestcoders.zaheed.presentation.components.SpacerWidthMediumLarge
import com.bestcoders.zaheed.presentation.components.StoreInfoShortcutComponent
import com.bestcoders.zaheed.presentation.screens.cart.components.NoCartItemsFoundComponent
import com.bestcoders.zaheed.presentation.screens.checkout.components.CheckoutProductComponent
import com.bestcoders.zaheed.presentation.screens.checkout.components.RemoveProductFromCartDialog
import com.bestcoders.zaheed.ui.theme.AppTheme
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CheckoutScreen(
    state: MutableState<CheckoutState>,
    onEvent: (CheckoutEvent) -> Unit,
    resetState: (CheckoutState) -> Unit,
    uiEvent: SharedFlow<UiEvent>,
    navigateBack: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToConfirmOrderScreen: (storeId: Int) -> Unit
) {
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val lazyListState = rememberLazyListState()

    val dimens = AppTheme.dimens

    val context = LocalContext.current

    val showRemoveProductDialog = remember {
        mutableStateOf(false)
    }
    val productToRemove = remember {
        mutableStateOf<Product?>(null)
    }

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

    LaunchedEffect(state.value.removeProductFromCartSuccess) {
        if (state.value.removeProductFromCartSuccess && state.value.error.isNullOrEmpty()) {
            if (state.value.cartByStoreModel!!.cart.products.isEmpty()) {
                productToRemove.value = null
                navigateBack()
            } else {
                productToRemove.value = null
                resetState(state.value.copy(removeProductFromCartSuccess = false))
            }
            return@LaunchedEffect
        }
    }

    LaunchedEffect(state.value.changeProductQuantitySuccess){
        if(state.value.changeProductQuantitySuccess){
            resetState(state.value.copy(changeProductQuantitySuccess = false))
        }
    }

    // If there is error show error message and navigate back to cart screen
    LaunchedEffect(state.value.error) {
        if (!state.value.changeProductQuantitySuccess && !state.value.error.isNullOrEmpty()) {
            onEvent(CheckoutEvent.ShowSnackBar(context.getString(R.string.product_max_items_reached)))
            return@LaunchedEffect
        } else if (!state.value.error.isNullOrEmpty()) {
            onEvent(CheckoutEvent.ShowSnackBar(state.value.error.toString()))
            navigateBack()
            return@LaunchedEffect
        }
        resetState(state.value.copy(error = null))
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            PrimaryTopBarBigTitle(
                title = stringResource(R.string.checkout),
                onBackClicked = navigateBack
            )
        },
        bottomBar = {
            if (state.value.cartByStoreModel != null) {
                CheckoutAndConfirmOrderScreenFooter(
                    buttonLabel = stringResource(id = R.string.continue_text),
                    summary = state.value.cartByStoreModel!!.summary,
                    onContinueButtonClick = {
                        navigateToConfirmOrderScreen(state.value.cartByStoreModel!!.cart.id)
                    }
                )
            }
        },
        content = { padding ->
            if (state.value.cartByStoreModel?.cart != null && !state.value.isLoading) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .imePadding()
                        .padding(
                            start = dimens.paddingHorizontal,
                            end = dimens.paddingHorizontal,
                            top = padding.calculateTopPadding(),
                            bottom = padding.calculateBottomPadding(),
                        ),
                    state = lazyListState,
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    content = {
                        item {
                            StoreInfoShortcutComponent(
                                modifier = Modifier.padding(horizontal = AppTheme.dimens.paddingHorizontal),
                                logo = state.value.cartByStoreModel!!.cart.logo,
                                name = state.value.cartByStoreModel!!.cart.name,
                                address = state.value.cartByStoreModel!!.cart.address,
                            )
                        }
                        items(
                            count = state.value.cartByStoreModel!!.cart.products.size,
                            key = {
                                state.value.cartByStoreModel!!.cart.products[it].hashCode()
                            },
                            itemContent = { index ->
                                CheckoutProductComponent(
                                    product = state.value.cartByStoreModel!!.cart.products[index],
                                    changeProductQuantity = { productId, variant, quantity, productPrice ->
                                        onEvent(
                                            CheckoutEvent.ChangeProductQuantity(
                                                productId, variant, quantity
                                            )
                                        )
                                    },
                                    state = state,
                                    removeProduct = {
                                        productToRemove.value = state.value.cartByStoreModel!!.cart.products[index]
                                        showRemoveProductDialog.value = true
                                    }
                                )
                                if (index == state.value.cartByStoreModel!!.cart.products.size - 1) {
                                    SpacerWidthMediumLarge()
                                }
                            },
                        )
                    },
                )
                if (showRemoveProductDialog.value) {
                    RemoveProductFromCartDialog(
                        onConfirm = {
                            onEvent(
                                CheckoutEvent.RemoveProductFromCart(
                                    productToRemove.value!!.id.toString(),
                                    productToRemove.value!!.variant!!
                                )
                            )
                        },
                        onDismiss = {
                            showRemoveProductDialog.value = false
                        }
                    )
                }
            } else if (state.value.cartByStoreModel?.cart == null && !state.value.isLoading) {
                NoCartItemsFoundComponent(
                    onStartShoppingClick = navigateToHome
                )
            } else {
                PrimaryProgress()
            }
        },
    )
    PrimarySnackbar(snackbarHostState)
}
