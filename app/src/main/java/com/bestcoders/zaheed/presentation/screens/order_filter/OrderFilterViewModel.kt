package com.bestcoders.zaheed.presentation.screens.order_filter

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.bestcoders.zaheed.core.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class OrderFilterViewModel @Inject constructor(
) : ViewModel() {

    var state = mutableStateOf(OrderFilterState())
        private set

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()
    fun resetState(newState: OrderFilterState?) {
        state.value = newState ?: OrderFilterState()
    }

    fun onEvent(event: OrderFilterEvent) {
        when (event) {

            else -> {}
        }
    }

}