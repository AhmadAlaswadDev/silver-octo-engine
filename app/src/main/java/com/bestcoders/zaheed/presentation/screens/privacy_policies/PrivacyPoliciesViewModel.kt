package com.bestcoders.zaheed.presentation.screens.privacy_policies

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bestcoders.zaheed.core.util.Resource
import com.bestcoders.zaheed.core.util.UiEvent
import com.bestcoders.zaheed.domain.use_case.GetPrivacyPoliciesUseCase
import com.bestcoders.zaheed.domain.use_case.GetTermsConditionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrivacyPoliciesViewModel @Inject constructor(
    private val getTermsConditionsUseCase: GetTermsConditionsUseCase,
    private val getPrivacyPoliciesUseCase: GetPrivacyPoliciesUseCase,
) : ViewModel() {

    var state by mutableStateOf(PrivacyPoliciesState())
        private set

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()


    private fun getTermsConditions() {
        getTermsConditionsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    state = state.copy(
                        isLoading = false,
                        success = true,
                        privacyPolicies = result.data,
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

    private fun getPrivacyPolicies() {
        getPrivacyPoliciesUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    state = state.copy(
                        isLoading = false,
                        success = true,
                        privacyPolicies = result.data,
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


    fun resetState(newState: PrivacyPoliciesState? = null) {
        state = newState ?: PrivacyPoliciesState()
    }

    fun onEvent(event: PrivacyPoliciesEvent) {
        when (event) {
            is PrivacyPoliciesEvent.ShowSnackBar -> {
                viewModelScope.launch {
                    _uiEvent.emit(UiEvent.ShowSnackbar(event.message, null))
                }
            }

            is PrivacyPoliciesEvent.GetPrivacyPolicies -> {
                getPrivacyPolicies()
            }

            is PrivacyPoliciesEvent.GetTermsConditions -> {
                getTermsConditions()
            }
        }
    }

}