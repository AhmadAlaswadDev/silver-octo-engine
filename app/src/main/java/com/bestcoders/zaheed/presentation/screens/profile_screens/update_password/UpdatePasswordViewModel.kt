package com.bestcoders.zaheed.presentation.screens.profile_screens.update_password


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bestcoders.zaheed.core.util.Resource
import com.bestcoders.zaheed.core.util.UiEvent
import com.bestcoders.zaheed.domain.use_case.UpdatePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdatePasswordViewModel @Inject constructor(
    private val updatePasswordUseCase: UpdatePasswordUseCase,
) : ViewModel() {

    var state = mutableStateOf(UpdatePasswordState())
        private set

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private fun updatePassword(
        oldPassword: String,
        newPassword: String,
    ) {
        updatePasswordUseCase(
            oldPassword = oldPassword,
            newPassword = newPassword
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    state.value = state.value.copy(
                        isLoading = false,
                        success = true,
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


    fun resetState(newState: UpdatePasswordState? = null) {
        state.value = newState ?: UpdatePasswordState()
    }

    fun onEvent(event: UpdatePasswordEvent) {
        when (event) {
            is UpdatePasswordEvent.ShowSnackBar -> {
                viewModelScope.launch {
                    _uiEvent.emit(UiEvent.ShowSnackbar(message = event.message))
                }
            }

            is UpdatePasswordEvent.OnUpdatePasswordClicked -> {
                updatePassword(
                    oldPassword = event.oldPassword,
                    newPassword = event.newPassword,
                )
            }
        }
    }

}