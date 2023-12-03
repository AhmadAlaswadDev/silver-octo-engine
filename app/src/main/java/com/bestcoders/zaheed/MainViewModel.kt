package com.bestcoders.zaheed

import android.app.LocaleManager
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.core.util.Resource
import com.bestcoders.zaheed.data.local.data_store.SettingsDataStore
import com.bestcoders.zaheed.data.local.entity.UserAuthDataStoreEntity
import com.bestcoders.zaheed.domain.use_case.CheckUserLoggedInUseCase
import com.bestcoders.zaheed.domain.use_case.GetSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val checkUserLoggedInUseCase: CheckUserLoggedInUseCase,
    private val getSettingsUseCase: GetSettingsUseCase,
    private val dataStore: DataStore<UserAuthDataStoreEntity>,
    private val settingsDataStore: SettingsDataStore,
    ) : ViewModel() {

    // Main State
    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

     fun checkUserLoggedIn() {
        checkUserLoggedInUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isUserLoggedIn = true
                        )
                    }
                }

                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isUserLoggedIn = false
                        )
                    }
                }

                is Resource.Loading -> {
                    _state.update { it.copy(isLoading = true) }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getSettings() {
        getSettingsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            settings = result.data!!
                        )
                    }
                    // Should read it from Api and chenge its language
                    Constants.MAIN_CURENCY = if (Constants.DEFAULT_LANGUAGE == Constants.ARABIC_LANGUAGE_CODE || Constants.DEFAULT_LANGUAGE == Constants.SAUDI_LANGUAGE_CODE) {
                        "ر.س"
                    } else {
                        "SR"
                    }
                }

                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            settings = null
                        )
                    }
                }

                is Resource.Loading -> {
                    _state.update { it.copy(isLoading = true) }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getUserToken() {
        viewModelScope.launch {
            dataStore.data.collectLatest {
                Constants.userToken = it.userToken ?: ""
                Constants.tempToken = it.tempToken ?: ""
                Constants.userName = it.name ?: ""
                Constants.userPhone = it.phoneNumber ?: ""
                Constants.userSaved = it.saved ?: "0"
                Constants.userEmail = it.email ?: ""
                Constants.userGender = it.gender ?: Constants.MALE
                Constants.userBirthDate = it.birthDate ?: ""
                Log.e("token", "getUserToken: ${it.userToken}")
            }
        }
    }

}