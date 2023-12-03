package com.bestcoders.zaheed.presentation.screens.profile_screens.order_history


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bestcoders.zaheed.core.extentions.enterAnimationItems
import com.bestcoders.zaheed.core.extentions.enterFadeInAnimation
import com.bestcoders.zaheed.core.extentions.exitAnimationItems
import com.bestcoders.zaheed.core.extentions.exitFadeOutAnimation
import com.bestcoders.zaheed.core.util.UiEvent
import com.bestcoders.zaheed.domain.model.products.OrderFilter
import com.bestcoders.zaheed.presentation.components.PaginationLoader
import com.bestcoders.zaheed.presentation.components.PrimaryProgress
import com.bestcoders.zaheed.presentation.components.PrimarySnackbar
import com.bestcoders.zaheed.presentation.components.SpacerHeightMediumLarge
import com.bestcoders.zaheed.presentation.screens.profile_screens.order_history.components.NoOrderItemsFoundComponent
import com.bestcoders.zaheed.presentation.screens.profile_screens.order_history.components.OrderComponent
import com.bestcoders.zaheed.presentation.screens.profile_screens.order_history.components.OrderHistoryTopBar
import com.bestcoders.zaheed.ui.theme.AppTheme
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun OrderHistoryScreen(
    state: MutableState<OrderHistoryState>,
    onEvent: (OrderHistoryEvent) -> Unit,
    uiEvent: SharedFlow<UiEvent>,
    navigateBack: () -> Unit,
    navigateToOrderDetails: (orderId: Int) -> Unit,
    navigateToOrderFilterScreen: () -> Unit,
    resetState: (OrderHistoryState) -> Unit,
    orderFilter: OrderFilter,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val listState = rememberScrollState()
    val searchValue = remember {
        mutableStateOf("")
    }
    val scope = rememberCoroutineScope()

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
    LaunchedEffect(state.value.error) {
        if (!state.value.success && !state.value.error.isNullOrEmpty()) {
            onEvent(OrderHistoryEvent.ShowSnackBar(message = state.value.error.toString()))
            resetState(state.value.copy(error = null))
            return@LaunchedEffect
        }
    }
    LaunchedEffect(!listState.canScrollForward) {
        if (state.value.paginationMeta?.nextPage != null
            && !state.value.isLoading
            && !listState.canScrollForward
        ) {
            onEvent(
                OrderHistoryEvent.GetOrderHistory(
                    page = state.value.paginationMeta!!.nextPage!!,
                    orderStatus = orderFilter.orderFiltersStatus,
                    paymentStatus = orderFilter.orderPaymentStatus,
                    searchValue = searchValue.value
                )
            )
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            OrderHistoryTopBar(
                onBackClicked = navigateBack,
                onFilterClick = {
                    scope.launch {
                        listState.scrollTo(0)
                    }
                    navigateToOrderFilterScreen()
                },
                onSearchValueChange = { value ->
                    searchValue.value = value
                    scope.launch {
                        listState.scrollTo(0)
                    }
                    if (value.isNotEmpty()) {
                        resetState(
                            state.value.copy(
                                orders = mutableStateListOf(),
                                paginationMeta = null
                            )
                        )
                        onEvent(
                            OrderHistoryEvent.GetOrderHistory(
                                page = 1,
                                orderStatus = orderFilter.orderFiltersStatus,
                                paymentStatus = orderFilter.orderPaymentStatus,
                                searchValue = searchValue.value
                            )
                        )
                    }
                }
            )
        },
        content = { padding ->
            // Orders
            AnimatedVisibility(
                visible = state.value.orders.isNotEmpty(),
                enter = enterAnimationItems(),
                exit = exitAnimationItems()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .imePadding()
                        .padding(
                            top = padding.calculateTopPadding(),
                            bottom = padding.calculateBottomPadding(),
                            start = AppTheme.dimens.paddingHorizontal,
                            end = AppTheme.dimens.paddingHorizontal,
                        ).verticalScroll(listState),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.mediumLarge),
                ) {
                    SpacerHeightMediumLarge()
                    state.value.orders.forEach {order ->
                        OrderComponent(
                            order = order,
                            onMoreDerailsClick = navigateToOrderDetails
                        )
                    }
                    if (state.value.isLoading) {
                        PaginationLoader()
                    }
                }
            }
            // No Order Items Found Component
            AnimatedVisibility(
                visible = state.value.orders.isEmpty() && !state.value.isLoading,
                enter = enterFadeInAnimation(),
                exit = exitFadeOutAnimation()
            ) {
                NoOrderItemsFoundComponent()
            }
            // Loading
            AnimatedVisibility(
                visible = state.value.isLoading && state.value.orders.isEmpty(),
                enter = enterFadeInAnimation(),
                exit = exitFadeOutAnimation()
            ) {
                PrimaryProgress()
            }
            PrimarySnackbar(snackbarHostState)
        }
    )
}


