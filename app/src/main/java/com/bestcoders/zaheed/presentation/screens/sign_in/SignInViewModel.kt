package com.bestcoders.zaheed.presentation.screens.sign_in

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bestcoders.zaheed.core.util.Resource
import com.bestcoders.zaheed.core.util.UiEvent
import com.bestcoders.zaheed.domain.use_case.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    var state by mutableStateOf(SignInState())
        private set

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private fun sendVerificationCodeLogin(
        phoneNumber: String,
    ) {
        loginUseCase(
            phoneNumber = phoneNumber,
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    state = state.copy(
                        isLoading = false,
                        success  = true,
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

    fun resetState(newState: SignInState? = null) {
        state = newState ?: SignInState()
    }

    fun onEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.OnSendVerificationCodeClicked -> {
                sendVerificationCodeLogin(
                    phoneNumber = event.phoneNumber,
                )
            }
        }
    }

}