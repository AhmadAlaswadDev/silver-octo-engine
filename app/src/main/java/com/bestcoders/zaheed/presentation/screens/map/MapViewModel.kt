package com.bestcoders.zaheed.presentation.screens.map

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bestcoders.zaheed.core.util.Resource
import com.bestcoders.zaheed.core.util.UiEvent
import com.bestcoders.zaheed.domain.use_case.GetUserLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getUserLocationUseCase: GetUserLocationUseCase,
) : ViewModel() {

    var state = mutableStateOf(MapState())
        private set

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        getUserLocation()
    }

    private fun getUserLocation() {
        getUserLocationUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val userLocation = result.data
                    state.value = state.value.copy(
                        success = true,
                        userLatitude = userLocation!!.latitude,
                        userLongitude = userLocation.longitude,
                    )
                }

                is Resource.Error -> {
                    state.value = state.value.copy(
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


    fun resetState(newState: MapState? = null) {
        state.value = newState ?: MapState()
    }

    fun onEvent(event: MapEvent) {
        when (event) {

            is MapEvent.ShowSnackBar -> {
                viewModelScope.launch {
                    _uiEvent.emit(UiEvent.ShowSnackbar(message = event.message, action = event.action))
                }
            }
        }
    }

}