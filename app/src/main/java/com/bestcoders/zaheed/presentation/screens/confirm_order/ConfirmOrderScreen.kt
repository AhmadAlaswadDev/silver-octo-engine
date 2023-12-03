package com.bestcoders.zaheed.presentation.screens.confirm_order

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.extentions.navigateToLocationInGoogleMaps
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.core.util.UiEvent
import com.bestcoders.zaheed.domain.model.products.PaymentSuccessData
import com.bestcoders.zaheed.domain.model.track.PickupPoint
import com.bestcoders.zaheed.presentation.components.CheckoutAndConfirmOrderScreenFooter
import com.bestcoders.zaheed.presentation.components.DateTimePickerUI
import com.bestcoders.zaheed.presentation.components.PrimaryProgress
import com.bestcoders.zaheed.presentation.components.PrimarySnackbar
import com.bestcoders.zaheed.presentation.components.PrimaryTopBarBigTitle
import com.bestcoders.zaheed.presentation.components.SpacerWidthMediumLarge
import com.bestcoders.zaheed.presentation.components.StoreInfoShortcutComponent
import com.bestcoders.zaheed.presentation.screens.confirm_order.components.ConfirmOrderProductComponent
import com.bestcoders.zaheed.presentation.screens.confirm_order.components.PaymentMethodSheet
import com.bestcoders.zaheed.presentation.screens.confirm_order.components.PickupDateTimeSelectorComponent
import com.bestcoders.zaheed.presentation.screens.confirm_order.components.PickupLocationMenuComponent
import com.bestcoders.zaheed.ui.theme.AppTheme
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ConfirmOrderScreen(
    state: MutableState<ConfirmOrderState>,
    onEvent: (ConfirmOrderEvent) -> Unit,
    resetState: (ConfirmOrderState) -> Unit,
    uiEvent: SharedFlow<UiEvent>,
    navigateToPaymentMethod: (telrUrl: String, orderId: Int) -> Unit,
    onBackClick: () -> Unit,
) {

    val snackbarHostState = remember { SnackbarHostState() }
    val pickupDateTime = remember { mutableStateOf(Constants.PICKUP_TIME) }
    val pickupDate = remember { mutableStateOf("") }
    val pickupTime = remember { mutableStateOf("") }
    val paymentType = remember { mutableStateOf(Constants.ONLINE_PAYMENT) }
    val pickupLocation = remember { mutableStateOf<PickupPoint?>(null) }
    val name by remember { mutableStateOf(Constants.userName) }
    val phoneNumber by remember { mutableStateOf(Constants.userPhone) }
    val showErrorValidation = remember { mutableStateOf(false) }
    var paymentMethodBottomSheet by remember { mutableStateOf(false) }

    val showSelector = rememberSaveable { mutableStateOf(true) }
    val showDateTimePicker = remember { mutableStateOf(false) }
    val selectedOption = remember { mutableStateOf(0) }
    val dateText = rememberSaveable { mutableStateOf("") }
    val timeText = rememberSaveable { mutableStateOf("") }

    val lazyListState = rememberLazyListState()
    val dimens = AppTheme.dimens
    val context = LocalContext.current

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

    LaunchedEffect(state.value.storePickupLocationsSuccess) {
        if (state.value.storePickupLocationsSuccess && !state.value.pickupPoints.isNullOrEmpty()) {
            pickupLocation.value = state.value.pickupPoints!![0]
            return@LaunchedEffect
        }
    }

    LaunchedEffect(state.value.getTelrUrlSuccess) {
        if (state.value.getTelrUrlSuccess && !state.value.telrUrl.isNullOrEmpty() && state.value.error.isNullOrEmpty()) {
            navigateToPaymentMethod(
                state.value.telrUrl!!,
                if (state.value.cartByStoreModel!!.cart.hasPendingOrder) {
                    state.value.cartByStoreModel!!.cart.orderId!!.toInt()
                } else {
                    state.value.orderId!!
                },
            )
            resetState(
                state.value.copy(
                    getTelrUrlSuccess = false,
                    telrUrl = null,
                )
            )
            return@LaunchedEffect
        }
    }

    LaunchedEffect(state.value.error) {
        if (!state.value.createOrderSuccess && !state.value.error.isNullOrEmpty()) {
            onEvent(ConfirmOrderEvent.ShowSnackBar(message = state.value.error.toString()))
            resetState(state.value.copy(error = null))
            return@LaunchedEffect
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            PrimaryTopBarBigTitle(
                title = stringResource(R.string.confirm_order),
                onBackClicked = onBackClick,
            )
        },
        bottomBar = {
            if (state.value.cartByStoreModel != null) {
                CheckoutAndConfirmOrderScreenFooter(
                    buttonLabel = stringResource(id = R.string.place_order),
                    summary = state.value.cartByStoreModel!!.summary,
                    onContinueButtonClick = {
                        showErrorValidation.value = true
                        if (state.value.cartByStoreModel!!.cart.hasPendingOrder) {
                            onEvent(
                                ConfirmOrderEvent.UpdateOrder(
                                    orderId = state.value.cartByStoreModel!!.cart.orderId!!.toString(),
                                    paymentType = paymentType.value,
                                    preferredTimeToPickUp = pickupDateTime.value,
                                    pickupLocationId = pickupLocation.value!!.id.toString(),
                                )
                            )
                        } else {
                            onEvent(
                                ConfirmOrderEvent.CreateOrder(
                                    storeId = state.value.cartByStoreModel!!.cart.id.toString(),
                                    paymentType = paymentType.value,
                                    preferredTimeToPickUp = pickupDateTime.value,
                                    pickupLocationId = pickupLocation.value!!.id.toString(),
                                )
                            )
                        }
                    }
                )
            }
        },
        content = { padding ->
            if (state.value.cartByStoreModel != null && state.value.pickupPoints != null) {
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
                                ConfirmOrderProductComponent(
                                    product = state.value.cartByStoreModel!!.cart.products[index]
                                )
                                if (index == state.value.cartByStoreModel!!.cart.products.size - 1) {
                                    SpacerWidthMediumLarge()
                                }
                            },
                        )
                        item {
                            PickupLocationMenuComponent(
                                pickupLocations = state.value.pickupPoints!!,
                                selectedPickupLocation = pickupLocation,
                                onPickupLocationClick = {
                                    navigateToLocationInGoogleMaps(
                                        context = context,
                                        locationQuery = state.value.cartByStoreModel!!.cart.address
                                    )
                                }
                            )
                        }
                        // Deleted
//                        item {
//                            ContactDetailsComponent(
//                                modifier = Modifier.padding(horizontal = AppTheme.dimens.paddingHorizontal),
//                                name = name,
//                                phoneNumber = phoneNumber,
//                            )
//                        }
                        item {
                            PickupDateTimeSelectorComponent(
                                showSelector = showSelector,
                                showDateTimePicker = showDateTimePicker,
                                selectedOption = selectedOption,
                                dateText = dateText,
                                timeText = timeText,
                            )
                        }
                        item {
                            PaymentMethodSheet(
                                modifier = Modifier,
                                onChoosePaymentClick = {
                                    paymentMethodBottomSheet = true
                                }
                            )
                        }
                    },
                )
            }
            if (state.value.isLoading) {
                PrimaryProgress()
            }

            if (showDateTimePicker.value) {
                DateTimePickerUI(
                    showDateTimePicker = showDateTimePicker,
                    onDismissRequest = { date, time ->
                        if (date.isNotEmpty() && time.isNotEmpty()) {
                            dateText.value = date
                            timeText.value = time
                            pickupDateTime.value = "$date $time"
                            pickupDate.value = date
                            pickupTime.value = time
                            showSelector.value = false
                        } else {
                            showSelector.value = true
                        }
                    },
                )
            } else if (!showDateTimePicker.value && dateText.value.isEmpty() && timeText.value.isEmpty()) {
                selectedOption.value = 0
            }
            if (paymentMethodBottomSheet) {
                PaymentMethodSheet(
                    onDismiss = {
                        paymentMethodBottomSheet = false
                    }
                )
            }

        },
    )

    PrimarySnackbar(snackbarHostState)


}

