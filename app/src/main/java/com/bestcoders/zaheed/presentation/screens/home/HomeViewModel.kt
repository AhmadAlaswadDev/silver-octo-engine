package com.bestcoders.zaheed.presentation.screens.home

import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bestcoders.zaheed.core.util.Resource
import com.bestcoders.zaheed.core.util.UiEvent
import com.bestcoders.zaheed.data.local.entity.UserAuthDataStoreEntity
import com.bestcoders.zaheed.domain.use_case.AddProductToFavoriteUseCase
import com.bestcoders.zaheed.domain.use_case.GetUserLocationUseCase
import com.bestcoders.zaheed.domain.use_case.RemoveProductFromFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserLocationUseCase: GetUserLocationUseCase,
    private val addProductToFavoriteUseCase: AddProductToFavoriteUseCase,
    private val removeProductFromFavoriteUseCase: RemoveProductFromFavoriteUseCase,
    private val dataStore: DataStore<UserAuthDataStoreEntity>
) : ViewModel() {

    var state = mutableStateOf(HomeState())
        private set

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        getUserToken()
        getUserLocation()
    }

    private fun getUserLocation() {
        getUserLocationUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val userLocation = result.data
                    state.value = state.value.copy(
                        isLoading = false,
                        userLatitude = userLocation!!.latitude,
                        userLongitude = userLocation.longitude,
                    )
                }

                is Resource.Error -> {
                    state.value = state.value.copy(
                        isLoading = false,
                    )
                }

                is Resource.Loading -> {
                    state.value = state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun addProductToFavorite(productId: Int) {
        if(state.value.userToken.isNotEmpty()) {
            addProductToFavoriteUseCase(
                productId = productId,
                userToken = state.value.userToken
            ).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        state.value = state.value.copy(
                            isLoading = false,
                            isProductAddedToFavorite = true,
                            error = null,
                        )
                    }

                    is Resource.Error -> {
                        state.value = state.value.copy(
                            isLoading = false,
                            isProductAddedToFavorite = false,
                            error = result.message
                        )
                    }

                    is Resource.Loading -> {
//                    state.value = state.value.copy(isLoading = true)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun removeProductFromFavorite(productId: Int) {
        if(state.value.userToken.isNotEmpty()) {
            removeProductFromFavoriteUseCase(
                productId = productId,
                userToken = state.value.userToken
            ).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        state.value = state.value.copy(
                            isLoading = false,
                            isProductRemovedFromFavorite = true,
                            error = null,
                        )
                    }

                    is Resource.Error -> {
                        state.value = state.value.copy(
                            isLoading = false,
                            isProductRemovedFromFavorite = false,
                            error = result.message
                        )
                    }

                    is Resource.Loading -> {
//                    state.value = state.value.copy(isLoading = true)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

     fun getUserToken() {
        viewModelScope.launch {
            dataStore.data.collectLatest {
                state.value = state.value.copy(userToken = it.userToken ?: "")
            }
        }
    }

    fun resetState(newState: HomeState? = null) {
        state.value = newState ?: HomeState()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnFavoriteClick -> {
                if(event.isFavorite){
                    removeProductFromFavorite(event.productId)
                }else{
                    addProductToFavorite(event.productId)

                }
            }

            is HomeEvent.GetUserLocation -> {
                getUserLocation()
            }

            is HomeEvent.ShowSnackBar -> {
                viewModelScope.launch {
                    _uiEvent.emit(UiEvent.ShowSnackbar(message = event.message, action = event.action))
                }
            }
        }
    }

}