package com.bestcoders.zaheed.presentation.screens.profile_screens.about_us

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bestcoders.zaheed.core.util.Resource
import com.bestcoders.zaheed.core.util.UiEvent
import com.bestcoders.zaheed.domain.use_case.GetAboutUsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AboutUsViewModel @Inject constructor(
    private val getAboutUsUseCase: GetAboutUsUseCase
) : ViewModel() {

    var state by mutableStateOf(AboutUsState())
        private set

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()


    private fun getAboutUs() {
        getAboutUsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    resetState(
                        state.copy(
                            isLoading = false,
                            success = true,
                            aboutUs = result.data,
                            error = null,
                        )
                    )
                }

                is Resource.Error -> {
                    resetState(
                        state.copy(
                            isLoading = false,
                            success = false,
                            error = result.message
                        )
                    )
                }

                is Resource.Loading -> {
                    resetState(
                        state.copy(isLoading = true)
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun resetState(newState: AboutUsState? = null) {
        state = newState ?: AboutUsState()
    }

    fun onEvent(event: AboutUsEvent) {
        when (event) {
            is AboutUsEvent.ShowSnackBar -> {
                viewModelScope.launch {
                    _uiEvent.emit(UiEvent.ShowSnackbar(event.message, null))
                }
            }

            is AboutUsEvent.GetAboutUs -> {
                getAboutUs()
            }

        }
    }

}