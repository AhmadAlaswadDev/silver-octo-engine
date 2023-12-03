package com.bestcoders.zaheed.presentation.screens.order_filter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.core.util.UiEvent
import com.bestcoders.zaheed.core.util.header
import com.bestcoders.zaheed.domain.model.products.OrderFilter
import com.bestcoders.zaheed.presentation.components.NavigationTopBar
import com.bestcoders.zaheed.presentation.components.PrimarySnackbar
import com.bestcoders.zaheed.presentation.components.SpacerHeightLarge
import com.bestcoders.zaheed.presentation.components.SpacerHeightMedium
import com.bestcoders.zaheed.presentation.screens.filter.components.FilterFooter
import com.bestcoders.zaheed.presentation.screens.order_filter.component.OrderFilterComponent
import com.bestcoders.zaheed.ui.theme.AppTheme
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun OrderFilterScreen(
    state: MutableState<OrderFilterState>,
    onEvent: (OrderFilterEvent) -> Unit,
    onNavigateBackWithOrderFilter: (OrderFilter) -> Unit,
    clearOrderFilter: () -> Unit,
    onNavigateBack: () -> Unit,
    resetState: (OrderFilterState) -> Unit,
    uiEvent: SharedFlow<UiEvent>,
) {

    val snackbarHostState = remember { SnackbarHostState() }
    val orderFiltersStatusSelectedItem = remember { mutableStateOf(0) }
    val orderPaymentStatusSelectedItem = remember { mutableStateOf(0) }

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

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            NavigationTopBar(
                label =  stringResource(R.string.filter),
                onBackClicked = {
                    onNavigateBack()
                },
            )
        },
        content = { padding ->
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = AppTheme.dimens.paddingHorizontal,
                        end = AppTheme.dimens.paddingHorizontal,
                        top = padding.calculateTopPadding(),
                        bottom = padding.calculateBottomPadding()
                    ),
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.SpaceBetween,
                content = {
                    header {
                        Text(
                            text = stringResource(R.string.order_status),
                            style = MaterialTheme.typography.headlineSmall.copy(
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Start
                            ),
                        )
                    }
                    Constants.settings.orderFiltersStatus.forEachIndexed { index, s ->
                        item {
                            OrderFilterComponent(
                                index = index,
                                selectedItem = orderFiltersStatusSelectedItem,
                                listValues = Constants.settings.orderFiltersStatus.map { it.value },
                                listNames = Constants.settings.orderFiltersStatus.map { it.name },
                                onClick = {
                                    resetState(state.value.copy(orderFiltersStatus = it))
                                }
                            )
                        }
                    }
                    header {
                        SpacerHeightMedium()
                    }
                    header {
                        Text(
                            text = stringResource(R.string.order_payment_status),
                            style = MaterialTheme.typography.headlineSmall.copy(
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Start
                            ),
                        )
                    }
                    Constants.settings.orderPaymentStatus.forEachIndexed { index, s ->
                        item {
                            OrderFilterComponent(
                                index = index,
                                listValues = Constants.settings.orderPaymentStatus.map { it.value },
                                listNames = Constants.settings.orderPaymentStatus.map { it.name },
                                selectedItem = orderPaymentStatusSelectedItem,
                                onClick = {
                                    resetState(state.value.copy(orderPaymentStatus = it))
                                },
                            )
                        }
                    }

                    header {
                        SpacerHeightMedium()
                    }
                    // Reset Apply buttons
                    header {
                        FilterFooter(
                            onResetClick = {
                                orderFiltersStatusSelectedItem.value = 0
                                orderPaymentStatusSelectedItem.value = 0
                                clearOrderFilter()
                            },
                            onApplyClick = {
                                onNavigateBackWithOrderFilter(
                                    OrderFilter(
                                        orderFiltersStatus = state.value.orderFiltersStatus,
                                        orderPaymentStatus = state.value.orderPaymentStatus,
                                    )
                                )
                            },
                        )
                    }
                    header {
                        SpacerHeightLarge()
                    }
                }
            )
        }
    )
    PrimarySnackbar(snackbarHostState)
}
