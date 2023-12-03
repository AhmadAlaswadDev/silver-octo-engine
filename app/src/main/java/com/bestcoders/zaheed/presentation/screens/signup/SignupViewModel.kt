package com.bestcoders.zaheed.presentation.screens.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bestcoders.zaheed.core.util.Resource
import com.bestcoders.zaheed.core.util.UiEvent
import com.bestcoders.zaheed.domain.model.auth.SignupData
import com.bestcoders.zaheed.domain.use_case.SignupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val signupUseCase: SignupUseCase,
) : ViewModel() {

    var state by mutableStateOf(SignupState())
        private set

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private fun signup(signupData: SignupData) {
        signupUseCase(
            signupData = signupData
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    state = state.copy(
                        isLoading = false,
                        success = true,
                        user = result.data,
                        error = null,
                    )
                }

                is Resource.Error -> {
                    state = state.copy(
                        isLoading = false,
                        success = false,
                        error = result.message
                    )
                }
                is Resource.Loading -> {
                    state = state.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
    fun resetState(newState: SignupState? = null) {
        state = newState ?: SignupState()
    }
    fun onEvent(event: SignupEvent) {
        when (event) {
            is SignupEvent.OnSignupClicked -> {
                signup(signupData = event.signupData)
            }

            is SignupEvent.ShowSnackBar -> {
                viewModelScope.launch {
                    _uiEvent.emit(UiEvent.ShowSnackbar(event.message, null))
                }
            }
        }
    }

}