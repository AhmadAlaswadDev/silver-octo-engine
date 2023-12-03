package com.bestcoders.zaheed.presentation.screens.profile_screens.profile


import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.core.util.Resource
import com.bestcoders.zaheed.core.util.UiEvent
import com.bestcoders.zaheed.data.local.data_store.SettingsDataStore
import com.bestcoders.zaheed.data.local.data_store.UserAuthDataStoreManager
import com.bestcoders.zaheed.data.local.entity.UserAuthDataStoreEntity
import com.bestcoders.zaheed.domain.use_case.DeleteUserUseCase
import com.bestcoders.zaheed.domain.use_case.LogoutUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val logoutUserUseCase: LogoutUserUseCase,
) : ViewModel() {

    var state = mutableStateOf(ProfileState())
        private set

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()



    private fun logoutUser() {
        logoutUserUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    Constants.removeUserData()
                    state.value = state.value.copy(
                        isLoading = false,
                        userLoggedOut  = true,
                    )
                }

                is Resource.Error -> {
                    state.value = state.value.copy(
                        isLoading = false,
                        userLoggedOut = false,
                        userLoggedOutError = result.message,
                    )
                }

                is Resource.Loading -> {
                    state.value = state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun changeLanguage(language: String,context: Context) {
        viewModelScope.launch {
            val settingsDataStore = SettingsDataStore(context)
            settingsDataStore.saveAppLanguage(language)
        }
    }

    private fun sendEmail(context: Context) {
        Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:${Constants.settings.contactEmail}")
            context.startActivity(this)
        }
    }

    fun resetState(newState: ProfileState? = null) {
        state.value = newState ?: ProfileState()
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.ShowSnackBar -> {
                viewModelScope.launch {
                    _uiEvent.emit(UiEvent.ShowSnackbar(message = event.message))
                }
            }

            ProfileEvent.OnLogOutClicked -> {
                logoutUser()
            }

            is ProfileEvent.ChangeLanguage -> {
                changeLanguage(event.language, event.context)
            }

            is ProfileEvent.OnContactDetailsClicked -> {
                sendEmail(context = event.context)
            }

        }
    }

}