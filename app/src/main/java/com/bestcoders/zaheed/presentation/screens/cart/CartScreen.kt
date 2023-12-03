package com.bestcoders.zaheed.presentation.screens.cart

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bestcoders.zaheed.core.extentions.enterAnimationItems
import com.bestcoders.zaheed.core.extentions.enterFadeInAnimation
import com.bestcoders.zaheed.core.extentions.exitAnimationItems
import com.bestcoders.zaheed.core.extentions.exitFadeOutAnimation
import com.bestcoders.zaheed.core.util.UiEvent
import com.bestcoders.zaheed.presentation.components.PrimaryProgress
import com.bestcoders.zaheed.presentation.components.PrimarySnackbar
import com.bestcoders.zaheed.presentation.screens.cart.components.CartComponent
import com.bestcoders.zaheed.presentation.screens.cart.components.CartTopBar
import com.bestcoders.zaheed.presentation.screens.cart.components.NoCartItemsFoundComponent
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CartScreen(
    state: MutableState<CartState>,
    onEvent: (CartEvent) -> Unit,
    resetState: (CartState) -> Unit,
    homePadding: PaddingValues,
    uiEvent: SharedFlow<UiEvent>,
    navigateToCheckout: (Int) -> Unit,
    navigateToProductDetails: (Int) -> Unit,
    navigateToHome: () -> Unit,
    cartItems: MutableState<Int>,
) {

    val snackbarHostState = remember {
        SnackbarHostState()
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

    LaunchedEffect(state.value.getCartsSuccess) {
        if (state.value.getCartsSuccess && state.value.cartModel?.carts != null) {
            cartItems.value = state.value.cartModel!!.carts.size
        }
    }

    LaunchedEffect(state.value.error) {
        if (state.value.cartModel != null && state.value.cartModel!!.carts.isEmpty()) {
            return@LaunchedEffect
        } else if (!state.value.error.isNullOrEmpty()) {
            onEvent(CartEvent.ShowSnackBar(state.value.error.toString()))
            return@LaunchedEffect
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CartTopBar()
        },
        content = { padding ->
            AnimatedVisibility(
                visible = !state.value.cartModel?.carts.isNullOrEmpty(),
                enter = enterAnimationItems(),
                exit = exitAnimationItems()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .imePadding()
                        .verticalScroll(rememberScrollState())
                        .padding(
                            top = padding.calculateTopPadding(),
                            bottom = homePadding.calculateBottomPadding()
                        ),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    content = {
                        state.value.cartModel?.carts?.forEach { cart ->
                            CartComponent(
                                cart = cart,
                                onCartClick = navigateToCheckout,
                                onProductClick = navigateToProductDetails
                            )
                        }
                    }
                )
            }
            // No Cart Items Found Component
            AnimatedVisibility(
                visible = state.value.cartModel?.carts.isNullOrEmpty() && !state.value.isLoading,
                enter = enterFadeInAnimation(),
                exit = exitFadeOutAnimation()
            ) {
                NoCartItemsFoundComponent(onStartShoppingClick = navigateToHome)
            }
            // Loading
            AnimatedVisibility(
                visible = state.value.isLoading && state.value.cartModel?.carts.isNullOrEmpty(),
                enter = enterFadeInAnimation(),
                exit = exitFadeOutAnimation()
            ) {
                PrimaryProgress()
            }
        },
    )
    PrimarySnackbar(snackbarHostState)
}
