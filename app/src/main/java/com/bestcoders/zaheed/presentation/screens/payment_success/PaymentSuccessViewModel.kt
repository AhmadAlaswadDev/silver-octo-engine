package com.bestcoders.zaheed.presentation.screens.payment_success

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.core.util.Resource
import com.bestcoders.zaheed.core.util.UiEvent
import com.bestcoders.zaheed.domain.use_case.GetPurchaseHistoryDetailsUseCase
import com.bestcoders.zaheed.domain.use_case.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentSuccessViewModel @Inject constructor(
    private val getPurchaseHistoryDetailsUseCase: GetPurchaseHistoryDetailsUseCase,
    private val getUserUseCase: GetUserUseCase,
) : ViewModel() {
    var state = mutableStateOf(PaymentSuccessState())
        private set

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private fun getPurchaseHistoryDetails(orderId: Int) {
        getPurchaseHistoryDetailsUseCase(
            orderId = orderId
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    getUser()
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

    private fun getUser() {
        getUserUseCase(
            token = Constants.userToken
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    resetState(
                        state.value.copy(
                            isLoading = false,
                            success = true,
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

    fun resetState(newState: PaymentSuccessState? = null) {
        state.value = newState ?: PaymentSuccessState()
    }

    fun onEvent(event: PaymentSuccessEvent) {
        when (event) {
            is PaymentSuccessEvent.ShowSnackBar -> {
                viewModelScope.launch {
                    _uiEvent.emit(UiEvent.ShowSnackbar(message = event.message))
                }
            }

            is PaymentSuccessEvent.GetOrderDetails -> {
                getPurchaseHistoryDetails(orderId = event.orderId)
            }
        }
    }

}