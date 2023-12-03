package com.bestcoders.zaheed.presentation.screens.profile_screens.order_details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bestcoders.zaheed.core.util.Resource
import com.bestcoders.zaheed.core.util.UiEvent
import com.bestcoders.zaheed.domain.use_case.GetPurchaseHistoryDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderDetailsViewModel @Inject constructor(
    private val getPurchaseHistoryDetailsUseCase: GetPurchaseHistoryDetailsUseCase,
) : ViewModel() {

    var state = mutableStateOf(OrderDetailsState())
        private set

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private fun getPurchaseHistoryDetails(orderId: Int) {
        getPurchaseHistoryDetailsUseCase(
            orderId = orderId
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    resetState(
                        state.value.copy(
                            isLoading = false,
                            success = true,
                            orderDetails = result.data,
                        )
                    )
                }

                is Resource.Error -> {
                    resetState(
                        state.value.copy(
                            isLoading = false,
                            success = false,
                            error = result.message,
                        )
                    )
                }

                is Resource.Loading -> {
                    resetState(
                        state.value.copy(isLoading = true)
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun resetState(newState: OrderDetailsState? = null) {
        state.value = newState ?: OrderDetailsState()
    }

    fun onEvent(event: OrderDetailsEvent) {
        when (event) {
            is OrderDetailsEvent.ShowSnackBar -> {
                viewModelScope.launch {
                    _uiEvent.emit(UiEvent.ShowSnackbar(message = event.message))
                }
            }

            is OrderDetailsEvent.GetOrderDetails -> {
                getPurchaseHistoryDetails(orderId = event.orderId)
            }
        }
    }

}