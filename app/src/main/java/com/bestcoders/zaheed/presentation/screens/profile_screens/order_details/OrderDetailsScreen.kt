package com.bestcoders.zaheed.presentation.screens.profile_screens.order_details


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.extentions.color
import com.bestcoders.zaheed.core.extentions.formatDateTime
import com.bestcoders.zaheed.core.extentions.removePadding
import com.bestcoders.zaheed.core.util.UiEvent
import com.bestcoders.zaheed.presentation.components.CopyTextButton
import com.bestcoders.zaheed.presentation.components.LineDivider
import com.bestcoders.zaheed.presentation.components.MainPrice
import com.bestcoders.zaheed.presentation.components.NavigationTopBar
import com.bestcoders.zaheed.presentation.components.PaymentMethodComponent
import com.bestcoders.zaheed.presentation.components.PickupDateTimeComponent
import com.bestcoders.zaheed.presentation.components.PickupLocationComponent
import com.bestcoders.zaheed.presentation.components.PrimaryButton
import com.bestcoders.zaheed.presentation.components.PrimaryProgress
import com.bestcoders.zaheed.presentation.components.PrimarySnackbar
import com.bestcoders.zaheed.presentation.components.SpacerWidthMediumLarge
import com.bestcoders.zaheed.presentation.screens.profile_screens.order_details.components.OrderDetailsProductComponent
import com.bestcoders.zaheed.presentation.screens.profile_screens.order_history.components.NoOrderItemsFoundComponent
import com.bestcoders.zaheed.ui.theme.AppTheme
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import org.joda.time.LocalDate

@Composable
fun OrderDetailsScreen(
    state: MutableState<OrderDetailsState>,
    onEvent: (OrderDetailsEvent) -> Unit,
    uiEvent: SharedFlow<UiEvent>,
    navigateBack: () -> Unit,
    navigateToStoreDetailsScreen: (storeId: Int) -> Unit,
    navigateToTrackScreen: (orderId: Int) -> Unit,
    resetState: (OrderDetailsState) -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }



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
            onEvent(OrderDetailsEvent.ShowSnackBar(message = state.value.error.toString()))
            resetState(state.value.copy(error = null))
            return@LaunchedEffect
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            NavigationTopBar(
                label = stringResource(R.string.order_details),
                onBackClicked = navigateBack
            )
        },
        content = { padding ->
            if (state.value.orderDetails != null) {
                val order = state.value.orderDetails!!
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .imePadding()
                        .padding(
                            top = padding.calculateTopPadding(),
                            bottom = AppTheme.dimens.paddingVertical,
                            start = AppTheme.dimens.paddingHorizontal,
                            end = AppTheme.dimens.paddingHorizontal,
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.mediumLarge),
                    content = {
                        // order status
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            content = {
                                Text(
                                    text = order.orderStatusString,
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        color = order.orderStatusColor.color,
                                        fontWeight = FontWeight.SemiBold,
                                    )
                                )
                                Text(
                                    text = order.date,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = MaterialTheme.colorScheme.secondary.copy(0.5f),
                                        fontWeight = FontWeight.SemiBold,
                                    )
                                )
                            }
                        )
                        // track order
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            content = {
                                Text(
                                    text = stringResource(id = R.string.track_order),
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        color = MaterialTheme.colorScheme.secondary.copy(0.5f),
                                        fontWeight = FontWeight.SemiBold,
                                    )
                                )
                                Row(
                                    modifier = Modifier.wrapContentHeight(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    Text(
                                        text = order.code,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = MaterialTheme.colorScheme.primary,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    )
                                    CopyTextButton(copyText = order.code)
                                }
                            }
                        )
                        // Products
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .removePadding(-AppTheme.dimens.paddingHorizontal),
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.Start,
                            content = {
                                items(
                                    count = order.store.products!!.size,
                                    key = {
                                        order.store.products[it].hashCode()
                                    },
                                    itemContent = { index ->
                                        SpacerWidthMediumLarge()
                                        OrderDetailsProductComponent(
                                            product = order.store.products[index],
                                            category = order.store.category!!
                                        )
                                        if (index == order.store.products.size - 1) {
                                            SpacerWidthMediumLarge()
                                        }
                                    }
                                )
                            },
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            content = {
                                Text(
                                    text = stringResource(R.string.total),
                                    overflow = TextOverflow.Clip,
                                    maxLines = 1,
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontWeight = FontWeight.SemiBold,
                                        textAlign = TextAlign.Start,
                                        color = MaterialTheme.colorScheme.primary
                                    ),
                                )
                                MainPrice(
                                    price = order.grandTotal.toString(),
                                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Start,
                                        color = MaterialTheme.colorScheme.primary,
                                    )
                                )
                            },
                        )
                        LineDivider(
                            modifier = Modifier
                                .removePadding(-AppTheme.dimens.paddingHorizontal)
                                .background(MaterialTheme.colorScheme.primary)
                        )
                        PickupLocationComponent(pickupLocation = order.pickupPoint!!)
                        if(order.preferredTimeToPickUp != "0000-00-00 00:00:00"){
                            val dateTime by remember {
                                derivedStateOf { LocalDate().formatDateTime(order.preferredTimeToPickUp!!) }
                            }
                            PickupDateTimeComponent(
                                pickupDate = dateTime.first,
                                pickupTime = dateTime.second
                            )
                        }else{
                            PickupDateTimeComponent(
                                pickupDate = "",
                                pickupTime = ""
                            )
                        }
                        PaymentMethodComponent()
                        PrimaryButton(
                            modifier = Modifier.width(AppTheme.dimens.subscribeButtonStoreDetailsWidth),
                            text = stringResource(R.string.track_order),
                            borderStroke = 1.5f,
                            color = Color.Transparent,
                            borderColor = MaterialTheme.colorScheme.onTertiary,
                            textStyle = MaterialTheme.typography.bodyLarge.copy(
                                color = MaterialTheme.colorScheme.onTertiary,
                                fontWeight = FontWeight.SemiBold,
                            ),
                            onClick = {
                                navigateToTrackScreen(order.combinedOrderId)
                            },
                        )
                        PrimaryButton(
                            modifier = Modifier.width(AppTheme.dimens.subscribeButtonStoreDetailsWidth),
                            text = stringResource(R.string.visit_store),
                            borderStroke = 1.5f,
                            color = Color.Transparent,
                            borderColor = MaterialTheme.colorScheme.onPrimary,
                            textStyle = MaterialTheme.typography.bodyLarge.copy(
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontWeight = FontWeight.SemiBold,
                            ),
                            onClick = {
                                navigateToStoreDetailsScreen(order.store.id)
                            },
                        )
                    },
                )

            } else if (state.value.orderDetails != null && !state.value.isLoading) {
                NoOrderItemsFoundComponent()
            } else {
                PrimaryProgress()
            }
        },
    )
    PrimarySnackbar(snackbarHostState)
}


