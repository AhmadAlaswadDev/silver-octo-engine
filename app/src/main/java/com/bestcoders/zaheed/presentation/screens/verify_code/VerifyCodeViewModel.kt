package com.bestcoders.zaheed.presentation.screens.verify_code

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.core.util.Resource
import com.bestcoders.zaheed.core.util.UiEvent
import com.bestcoders.zaheed.data.local.entity.UserAuthDataStoreEntity
import com.bestcoders.zaheed.domain.use_case.ReSendVerificationCodeLoginUseCase
import com.bestcoders.zaheed.domain.use_case.ReSendVerificationCodeRegisterUseCase
import com.bestcoders.zaheed.domain.use_case.ValidateOTPDeleteUserUseCase
import com.bestcoders.zaheed.domain.use_case.ValidateOTPLoginUseCase
import com.bestcoders.zaheed.domain.use_case.ValidateOTPRegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerifyCodeViewModel @Inject constructor(
    private val reSendVerificationCodeLoginUseCase: ReSendVerificationCodeLoginUseCase,
    private val validateOTPLoginUseCase: ValidateOTPLoginUseCase,
    private val reSendVerificationCodeRegisterUseCase: ReSendVerificationCodeRegisterUseCase,
    private val validateOTPRegisterUseCase: ValidateOTPRegisterUseCase,
    private val validateOTPDeleteUserUseCase: ValidateOTPDeleteUserUseCase,
    private val dataStore: DataStore<UserAuthDataStoreEntity>,
    ) : ViewModel() {

    var state by mutableStateOf(VerifyCodeState())
        private set

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private var _timer: MutableStateFlow<Int> = MutableStateFlow(Constants.RESEND_OTP_TIME)
    val timer: StateFlow<Int> get() = _timer.asStateFlow()
    private var isTimerRunning = false


    init {
        startTimer()
    }

    private fun startTimer() = viewModelScope.launch {
        isTimerRunning = true
        while (isTimerRunning) {
            if (_timer.value <= 0) {
                isTimerRunning = false
                _timer.value = Constants.RESEND_OTP_TIME
                return@launch
            }
            delay(1000L)
            _timer.value--
        }
    }

    private fun validateOTPLogin(phoneNumber: String, verificationCode: String) {
        validateOTPLoginUseCase(
            phoneNumber = phoneNumber,
            verificationCode = verificationCode
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    state = state.copy(
                        isLoading = false,
                        success = true,
                        user = result.data,
                        verifyError = null,
                    )
                }

                is Resource.Error -> {
                    state = state.copy(
                        isLoading = false,
                        success = false,
                        verifyError = result.message
                    )
                }

                is Resource.Loading -> {
                    state = state.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun reSendVerificationCodeLogin(phoneNumber: String) {
        reSendVerificationCodeLoginUseCase(
            phoneNumber = phoneNumber,
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    startTimer()
                    state = state.copy(
                        isLoading = false,
                        reSendCodeSuccess = true,
                    )
                }

                is Resource.Error -> {
                    state = state.copy(
                        isLoading = false,
                        reSendCodeSuccess = false,
                    )
                }

                is Resource.Loading -> {
                    state = state.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun validateOTPRegister(phoneNumber: String, verificationCode: String) {
        validateOTPRegisterUseCase(
            phoneNumber = phoneNumber,
            verificationCode = verificationCode
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    state = state.copy(
                        isLoading = false,
                        success = true,
                        registrationRequestId = result.data,
                        verifyError = null,
                    )
                }

                is Resource.Error -> {
                    state = state.copy(
                        isLoading = false,
                        success = false,
                        verifyError = result.message
                    )
                }

                is Resource.Loading -> {
                    state = state.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun reSendVerificationCodeRegister(phoneNumber: String) {
        reSendVerificationCodeRegisterUseCase(
            phoneNumber = phoneNumber,
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    startTimer()
                    state = state.copy(
                        isLoading = false,
                        reSendCodeSuccess = true,
                    )
                }

                is Resource.Error -> {
                    state = state.copy(
                        isLoading = false,
                        reSendCodeSuccess = false,
                    )
                }

                is Resource.Loading -> {
                    state = state.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }


    private fun validateOTPDeleteUser(verificationCode: String) {
        validateOTPDeleteUserUseCase(
            verificationCode = verificationCode
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    Constants.removeUserData()
                    dataStore.updateData { UserAuthDataStoreEntity() }
                    state = state.copy(
                        isLoading = false,
                        verifyDeleteUserSuccess = true,
                        verifyDeleteUserError = null,
                    )
                }

                is Resource.Error -> {
                    state = state.copy(
                        isLoading = false,
                        verifyDeleteUserSuccess = false,
                        verifyDeleteUserError = result.message
                    )
                }

                is Resource.Loading -> {
                    state = state.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }


    fun resetState(newState: VerifyCodeState?) {
        state = newState ?: VerifyCodeState()
    }

    fun onEvent(event: VerifyCodeEvent) {
        when (event) {
            is VerifyCodeEvent.OnReSendCodeLoginClicked -> {
                reSendVerificationCodeLogin(phoneNumber = event.phoneNumber)
            }

            is VerifyCodeEvent.VerifyCodeLogin -> {
                validateOTPLogin(
                    verificationCode = event.verificationCode,
                    phoneNumber = event.phoneNumber,
                )
            }

            is VerifyCodeEvent.OnReSendCodeRegisterClicked -> {
                reSendVerificationCodeRegister(phoneNumber = event.phoneNumber)
            }

            is VerifyCodeEvent.VerifyCodeRegister -> {
                validateOTPRegister(
                    verificationCode = event.verificationCode,
                    phoneNumber = event.phoneNumber,
                )
            }
            is VerifyCodeEvent.VerifyCodeDeleteUser -> {
                validateOTPDeleteUser(
                    verificationCode = event.verificationCode,
                )
            }

            is VerifyCodeEvent.ShowSnackBar -> {
                viewModelScope.launch {
                    _uiEvent.emit(UiEvent.ShowSnackbar(message = event.message))
                }
            }
        }
    }

}