package com.bestcoders.zaheed.presentation.screens.profile_screens.edit_profile


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bestcoders.zaheed.core.util.Resource
import com.bestcoders.zaheed.core.util.UiEvent
import com.bestcoders.zaheed.domain.use_case.DeleteUserUseCase
import com.bestcoders.zaheed.domain.use_case.UpdateProfileUseCase
import com.bestcoders.zaheed.presentation.screens.profile_screens.profile.ProfileEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    ) : ViewModel() {

    var state = mutableStateOf(EditProfileState())
        private set

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private fun updateProfile(
        name: String? = null,
        email: String? = null,
        phoneNumber: String? = null,
        birthDate: String? = null,
        gender: String? = null,
    ) {
        updateProfileUseCase(
            name = name,
            email = email,
            phoneNumber = phoneNumber,
            birthDate = birthDate,
            gender = gender
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    resetState(
                        state.value.copy(
                            isLoading = false,
                            success = true,
                            error = null,
                        )
                    )
                }

                is Resource.Error -> {
                    resetState(
                        state.value.copy(
                            isLoading = false,
                            success = false,
                            error = result.message
                        )
                    )
                }

                is Resource.Loading -> {
                    resetState(state.value.copy(isLoading = true))
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun deleteUser() {
        deleteUserUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    resetState(
                        state.value.copy(
                            isLoading = false,
                            deleteUserSuccess = true,
                            deleteUserError = null,
                        )
                    )
                }

                is Resource.Error -> {
                    resetState(
                        state.value.copy(
                            isLoading = false,
                            deleteUserSuccess  = false,
                            deleteUserError = result.message
                        )
                    )
                }

                is Resource.Loading -> {
                    resetState(state.value.copy(isLoading = true))
                }
            }
        }.launchIn(viewModelScope)
    }

    fun resetState(newState: EditProfileState? = null) {
        state.value = newState ?: EditProfileState()
    }

    fun onEvent(event: EditProfileEvent) {
        when (event) {
            is EditProfileEvent.ShowSnackBar -> {
                viewModelScope.launch {
                    _uiEvent.emit(UiEvent.ShowSnackbar(message = event.message))
                }
            }

            is EditProfileEvent.OnEditClicked -> {
                updateProfile(
                    name = event.name,
                    email = event.email,
                    phoneNumber = event.phoneNumber,
                    birthDate = event.birthDate,
                    gender = event.gender
                )
            }
            is EditProfileEvent.OnDeleteUserClicked -> {
                deleteUser()
            }
        }
    }

}