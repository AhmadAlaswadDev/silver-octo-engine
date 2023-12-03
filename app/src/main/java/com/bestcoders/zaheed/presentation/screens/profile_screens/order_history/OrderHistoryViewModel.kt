package com.bestcoders.zaheed.presentation.screens.profile_screens.order_history

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bestcoders.zaheed.core.util.Resource
import com.bestcoders.zaheed.core.util.UiEvent
import com.bestcoders.zaheed.domain.use_case.GetPurchaseHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderHistoryViewModel @Inject constructor(
    private val getPurchaseHistoryUseCase: GetPurchaseHistoryUseCase,
) : ViewModel() {


    var state = mutableStateOf(OrderHistoryState())
        private set

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private fun getPurchaseHistory(
        page: Int,
        paymentStatus: String,
        orderStatus: String,
        searchValue: String,
    ) {
        getPurchaseHistoryUseCase(
            page = page,
            paymentStatus = paymentStatus,
            orderStatus = orderStatus,
            searchValue = searchValue,
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    resetState(
                        state.value.copy(
                            isLoading = false,
                            success = true,
                            orders = state.value.orders.plus(
                                result.data?.orders ?: mutableStateListOf()
                            ).toMutableStateList(),
                            paginationMeta = result.data?.paginationMeta,
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

    fun resetState(newState: OrderHistoryState? = null) {
        state.value = newState ?: OrderHistoryState()
    }

    fun onEvent(event: OrderHistoryEvent) {
        when (event) {
            is OrderHistoryEvent.ShowSnackBar -> {
                viewModelScope.launch {
                    _uiEvent.emit(UiEvent.ShowSnackbar(message = event.message))
                }
            }

            is OrderHistoryEvent.GetOrderHistory -> {
                getPurchaseHistory(
                    page = event.page,
                    paymentStatus = event.paymentStatus,
                    orderStatus = event.orderStatus,
                    searchValue = event.searchValue
                )
            }
        }
    }

}