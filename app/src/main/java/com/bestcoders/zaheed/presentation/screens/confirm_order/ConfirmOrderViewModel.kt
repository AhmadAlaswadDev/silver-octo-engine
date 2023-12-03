package com.bestcoders.zaheed.presentation.screens.confirm_order


import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bestcoders.zaheed.core.util.Resource
import com.bestcoders.zaheed.core.util.UiEvent
import com.bestcoders.zaheed.domain.use_case.CreateOrderUseCase
import com.bestcoders.zaheed.domain.use_case.GetCartByStoreUseCase
import com.bestcoders.zaheed.domain.use_case.GetStorePickupLocationsUseCase
import com.bestcoders.zaheed.domain.use_case.GetTelrUrlUseCase
import com.bestcoders.zaheed.domain.use_case.UpdateOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfirmOrderViewModel @Inject constructor(
    private val getCartByStoreUseCase: GetCartByStoreUseCase,
    private val getStorePickupLocationsUseCase: GetStorePickupLocationsUseCase,
    private val createOrderUseCase: CreateOrderUseCase,
    private val updateOrderUseCase: UpdateOrderUseCase,
    private val getTelrUrlUseCase: GetTelrUrlUseCase,
) : ViewModel() {

    var state = mutableStateOf(ConfirmOrderState())
        private set

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private fun getCartByStore(storeId: Int) {
        getCartByStoreUseCase(
            latitude = state.value.userLatitude.toString(),
            longitude = state.value.userLongitude.toString(),
            storeId = storeId
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    state.value = state.value.copy(
                        isLoading = false,
                        getCartByStoreSuccess = true,
                        cartByStoreModel = result.data
                    )
                }

                is Resource.Error -> {
                    state.value = state.value.copy(
                        isLoading = false,
                        getCartByStoreSuccess = false,
                        error = result.message,
                    )
                }

                is Resource.Loading -> {
                    state.value = state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getStorePickupLocations(storeId: Int) {
        getStorePickupLocationsUseCase(
            storeId = storeId
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    state.value = state.value.copy(
                        isLoading = false,
                        storePickupLocationsSuccess = true,
                        pickupPoints = result.data
                    )
                }

                is Resource.Error -> {
                    state.value = state.value.copy(
                        isLoading = false,
                        storePickupLocationsSuccess = false,
                        error = result.message,
                    )
                }

                is Resource.Loading -> {
                    state.value = state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun createOrder(
        storeId: String,
        paymentType: String,
        preferredTimeToPickUp: String,
        pickupLocationId: String,
    ) {
        createOrderUseCase(
            storeId = storeId,
            paymentType = paymentType,
            preferredTimeToPickUp = preferredTimeToPickUp,
            pickupLocationId = pickupLocationId
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    state.value = state.value.copy(
                        isLoading = false,
                        createOrderSuccess = true,
                        orderId = result.data,
                    )
                    getTelrUrl(result.data.toString())
                }

                is Resource.Error -> {
                    state.value = state.value.copy(
                        isLoading = false,
                        createOrderSuccess = false,
                        error = result.message,
                    )
                }

                is Resource.Loading -> {
                    state.value = state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun updateOrder(
        orderId: String,
        paymentType: String,
        preferredTimeToPickUp: String,
        pickupLocationId: String,
    ) {
        updateOrderUseCase(
            orderId = orderId,
            paymentType = paymentType,
            preferredTimeToPickUp = preferredTimeToPickUp,
            pickupLocationId = pickupLocationId
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    Log.e("ASHSFLHASFHASDF", "updateOrder: ${result.data}")
                    state.value = state.value.copy(
                        isLoading = false,
                        updateOrderSuccess = true,
                        orderId = result.data,
                    )
                    getTelrUrl(result.data.toString())
                }

                is Resource.Error -> {
                    state.value = state.value.copy(
                        isLoading = false,
                        updateOrderSuccess = false,
                        error = result.message,
                    )
                }

                is Resource.Loading -> {
                    state.value = state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getTelrUrl(
        orderId: String,
    ) {
        getTelrUrlUseCase(
            orderId = orderId,
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    state.value = state.value.copy(
                        isLoading = false,
                        getTelrUrlSuccess = true,
                        telrUrl = result.data,
                    )
                }

                is Resource.Error -> {
                    state.value = state.value.copy(
                        isLoading = false,
                        getTelrUrlSuccess = false,
                        error = result.message,
                    )
                }

                is Resource.Loading -> {
                    state.value = state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }


    fun resetState(newState: ConfirmOrderState? = null) {
        state.value = newState ?: ConfirmOrderState()
    }

    fun onEvent(event: ConfirmOrderEvent) {
        when (event) {
            is ConfirmOrderEvent.CreateOrder -> {
                createOrder(
                    storeId = event.storeId,
                    paymentType = event.paymentType,
                    preferredTimeToPickUp = event.preferredTimeToPickUp,
                    pickupLocationId = event.pickupLocationId,
                )
            }

            is ConfirmOrderEvent.UpdateOrder -> {
                updateOrder(
                    orderId = event.orderId,
                    paymentType = event.paymentType,
                    preferredTimeToPickUp = event.preferredTimeToPickUp,
                    pickupLocationId = event.pickupLocationId,
                )
            }

            is ConfirmOrderEvent.GetCartByStore -> {
                getCartByStore(storeId = event.storeId)
            }

            is ConfirmOrderEvent.GetStorePickupLocations -> {
                getStorePickupLocations(storeId = event.storeId)
            }

            is ConfirmOrderEvent.ShowSnackBar -> {
                viewModelScope.launch {
                    _uiEvent.emit(UiEvent.ShowSnackbar(message = event.message))
                }
            }
        }
    }

}