package com.bestcoders.zaheed.presentation.screens.track

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.util.UiEvent
import com.bestcoders.zaheed.presentation.components.AppError
import com.bestcoders.zaheed.presentation.components.NavigationTopBar
import com.bestcoders.zaheed.presentation.components.PrimaryProgress
import com.bestcoders.zaheed.presentation.components.PrimarySnackbar
import com.bestcoders.zaheed.presentation.components.StoreInfoShortcutComponent
import com.bestcoders.zaheed.presentation.screens.track.components.OrderConfirmedComponent
import com.bestcoders.zaheed.presentation.screens.track.components.OrderPickedComponent
import com.bestcoders.zaheed.presentation.screens.track.components.OrderPlacedComponent
import com.bestcoders.zaheed.presentation.screens.track.components.OrderReadyForPickupComponent
import com.bestcoders.zaheed.presentation.screens.track.components.TrackOrderFooterComponent
import com.bestcoders.zaheed.presentation.screens.track.components.TrackOrderIdComponent
import com.bestcoders.zaheed.ui.theme.AppTheme
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

@Composable
fun OrderTrackScreen(
    state: MutableState<OrderTrackState>,
    onEvent: (OrderTrackEvent) -> Unit,
    resetState: (OrderTrackState) -> Unit,
    uiEvent: SharedFlow<UiEvent>,
    onReloadClicked: () -> Unit,
    navigateBack: () -> Unit,
) {

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(true) {
        uiEvent.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action,
                    )
                }
            }
        }
    }

    LaunchedEffect(state.value.error) {
        if (!state.value.error.isNullOrEmpty()) {
            onEvent(OrderTrackEvent.ShowSnackBar(message = state.value.error.toString()))
            return@LaunchedEffect
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            NavigationTopBar(
                label = stringResource(R.string.delivery_status),
                onBackClicked = navigateBack
            )
        },
        content = { padding ->
            if (state.value.orderTrack != null && !state.value.isLoading) {
                val order = state.value.orderTrack!!.order
                val pickupPoint = state.value.orderTrack!!.pickupPoint
                val track = state.value.orderTrack!!.track
                Column(
                    modifier = Modifier.padding(padding),
                    verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.small),
                    content = {
                        StoreInfoShortcutComponent(
                            logo = order.store.logo,
                            name = order.store.name,
                            address = order.store.address,
                        )
                        TrackOrderIdComponent(orderCode = order.code)
                        OrderPlacedComponent(track)
                        OrderConfirmedComponent(track)
                        OrderReadyForPickupComponent(pickupPoint, track, order)
                        OrderPickedComponent(track)
                        if(!track.isOrderCompleted){
                            TrackOrderFooterComponent(startTime = track.orderConfirmedAt!!)
                        }
                    },
                )
            } else if (state.value.orderTrack == null && !state.value.isLoading && !state.value.error.isNullOrEmpty()) {
                AppError(
                    error = state.value.error.toString(),
                    onReloadClicked = onReloadClicked
                )
            } else if(state.value.isLoading) {
                PrimaryProgress()
            }
        }
    )
    PrimarySnackbar(snackbarHostState)

}
fun isoToLongTimestamp(isoTimestamp: String): Long {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.getDefault())
    dateFormat.timeZone = TimeZone.getTimeZone("UTC")
    val date = dateFormat.parse(isoTimestamp)
    return date?.time ?: 0L
}


