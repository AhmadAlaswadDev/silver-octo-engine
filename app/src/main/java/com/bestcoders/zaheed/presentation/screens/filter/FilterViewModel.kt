package com.bestcoders.zaheed.presentation.screens.filter

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.bestcoders.zaheed.core.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
) : ViewModel() {

    var state = mutableStateOf(FilterState())
        private set

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()
    fun resetState(newState: FilterState?) {
        state.value = newState ?: FilterState()
    }

    fun onEvent(event: FilterEvent) {
        when (event) {

            else -> {}
        }
    }

}