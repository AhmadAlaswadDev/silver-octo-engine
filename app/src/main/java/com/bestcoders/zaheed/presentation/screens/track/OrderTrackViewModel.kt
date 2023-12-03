package com.bestcoders.zaheed.presentation.screens.track

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bestcoders.zaheed.core.util.Resource
import com.bestcoders.zaheed.core.util.UiEvent
import com.bestcoders.zaheed.domain.use_case.GetOrderTrackUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderTrackViewModel @Inject constructor(
    private val getOrderTrackUseCase: GetOrderTrackUseCase,
) : ViewModel() {

    var state = mutableStateOf(OrderTrackState())
        private set

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private fun getOrderTrack(orderId: Int) {
        getOrderTrackUseCase(orderId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    state.value = state.value.copy(
                        isLoading = false,
                        success = true,
                        orderTrack = result.data
                    )
                }

                is Resource.Error -> {
                    state.value = state.value.copy(
                        isLoading = false,
                        success = false,
                        error = result.message,
                    )
                }

                is Resource.Loading -> {
                    state.value = state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun resetState(newState: OrderTrackState?) {
        state.value = newState ?: OrderTrackState()
    }

    fun onEvent(event: OrderTrackEvent) {
        when (event) {
            is OrderTrackEvent.GetOrderTrack -> {
                getOrderTrack(event.orderId)
            }

            is OrderTrackEvent.ShowSnackBar -> {
                viewModelScope.launch {
                    _uiEvent.emit(
                        UiEvent.ShowSnackbar(
                            message = event.message,
                            action = event.action
                        )
                    )
                }
            }
        }
    }

}