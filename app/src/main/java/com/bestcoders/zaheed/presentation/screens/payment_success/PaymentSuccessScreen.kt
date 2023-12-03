package com.bestcoders.zaheed.presentation.screens.payment_success

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.extentions.formatDateTime
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.core.util.UiEvent
import com.bestcoders.zaheed.presentation.components.CopyTextButton
import com.bestcoders.zaheed.presentation.components.PaymentMethodComponent
import com.bestcoders.zaheed.presentation.components.PickupDateTimeComponent
import com.bestcoders.zaheed.presentation.components.PickupLocationComponent
import com.bestcoders.zaheed.presentation.components.PrimaryButton
import com.bestcoders.zaheed.presentation.components.PrimarySnackbar
import com.bestcoders.zaheed.presentation.components.StoreInfoShortcutComponent
import com.bestcoders.zaheed.presentation.components.YouSavedBordered
import com.bestcoders.zaheed.presentation.screens.confirm_order.components.ConfirmOrderProductComponent
import com.bestcoders.zaheed.ui.theme.AppTheme
import com.bestcoders.zaheed.ui.theme.CustomColor
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import org.joda.time.LocalDate

@Composable
fun PaymentSuccessScreen(
    state: MutableState<PaymentSuccessState>,
    onEvent: (PaymentSuccessEvent) -> Unit,
    resetState: (PaymentSuccessState) -> Unit,
    uiEvent: SharedFlow<UiEvent>,
    navigateToHome: () -> Unit,
    navigateToTrackOrder: (orderId: Int) -> Unit,
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
            onEvent(PaymentSuccessEvent.ShowSnackBar(message = state.value.error.toString()))
            resetState(state.value.copy(error = null))
            return@LaunchedEffect
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { padding ->
            if (state.value.orderDetails != null && !state.value.isLoading) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .imePadding()
                        .padding(
                            horizontal = AppTheme.dimens.paddingHorizontal,
                            vertical = AppTheme.dimens.paddingVertical,
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    content = {
                        item {
                            Image(
                                modifier = Modifier.size(AppTheme.dimens.paymentSuccessIconSize),
                                painter = painterResource(id = R.drawable.payment_success_icon),
                                contentDescription = null
                            )
                        }
                        item {
                            Text(
                                text = stringResource(R.string.great),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                        item {
                            Text(
                                text = stringResource(R.string.payment_success),
                                style = MaterialTheme.typography.headlineLarge.copy(
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                        item {
                            YouSavedBordered(
                                saved = state.value.orderDetails!!.totalSaving
                            )
                        }
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(AppTheme.dimens.orderNumberColumnPaymentSuccessHeight)
                                    .background(
                                        color = MaterialTheme.colorScheme.secondaryContainer.copy(
                                            alpha = 0.05f
                                        ),
                                        shape = RoundedCornerShape(Constants.CORNER_RADUIES)
                                    )
                                    .padding(
                                        horizontal = AppTheme.dimens.extraLarge,
                                        vertical = AppTheme.dimens.large
                                    ),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(10.dp),
                                content = {
                                    Text(
                                        text = stringResource(R.string.your_order_number),
                                        style = MaterialTheme.typography.bodyLarge.copy(
                                            color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.4f),
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    )
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            text = state.value.orderDetails!!.code,
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                color = MaterialTheme.colorScheme.primary,
                                                fontWeight = FontWeight.SemiBold
                                            )
                                        )
                                        CopyTextButton(copyText = state.value.orderDetails!!.code)
                                    }
                                    PrimaryButton(
                                        text = stringResource(R.string.track_the_order),
                                        borderStroke = 1.5f,
                                        color = Color.Transparent,
                                        borderColor = MaterialTheme.colorScheme.onTertiary,
                                        textStyle = MaterialTheme.typography.bodyLarge.copy(
                                            color = MaterialTheme.colorScheme.onTertiary,
                                            fontWeight = FontWeight.SemiBold,
                                        ),
                                        onClick = {
                                            navigateToTrackOrder(state.value.orderDetails!!.combinedOrderId)
                                        },
                                    )
                                    PrimaryButton(
                                        text = stringResource(R.string.keep_shopping),
                                        textStyle = MaterialTheme.typography.bodyLarge.copy(
                                            color = CustomColor.White,
                                            fontWeight = FontWeight.SemiBold,
                                        ),
                                        onClick = navigateToHome,
                                    )
                                },
                            )
                        }
                        item {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = stringResource(R.string.order_details),
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.SemiBold,
                                    textAlign = TextAlign.Start,
                                )
                            )
                        }
                        item {
                            StoreInfoShortcutComponent(
                                logo = state.value.orderDetails!!.store.logo,
                                name = state.value.orderDetails!!.store.name,
                                address = state.value.orderDetails!!.store.address,
                            )
                        }
                        items(
                            count = state.value.orderDetails!!.store.products!!.size,
                            key = {
                                state.value.orderDetails!!.store.products!![it].hashCode()
                            },
                            itemContent = { index ->
                                ConfirmOrderProductComponent(
                                    product = state.value.orderDetails!!.store.products!![index],
                                )
                            },
                        )
                        item {
                            PickupLocationComponent(pickupLocation = state.value.orderDetails!!.pickupPoint!!)
                        }
                        item {

                            val dateTime by remember {
                                derivedStateOf { LocalDate().formatDateTime(state.value.orderDetails!!.preferredTimeToPickUp!!) }
                            }
                            PickupDateTimeComponent(
                                pickupDate = dateTime.first,
                                pickupTime = dateTime.second
                            )
                        }
                        item {
                            PaymentMethodComponent()
                        }
                    },
                )
            }
        },
    )
    PrimarySnackbar(snackbarHostState)
}



