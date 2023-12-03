package com.bestcoders.zaheed.presentation.screens.store_details


import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.core.util.Resource
import com.bestcoders.zaheed.core.util.UiEvent
import com.bestcoders.zaheed.domain.use_case.AddStoreToFavoriteUseCase
import com.bestcoders.zaheed.domain.use_case.FollowStoreUseCase
import com.bestcoders.zaheed.domain.use_case.GetStoreDetailsUseCase
import com.bestcoders.zaheed.domain.use_case.RemoveStoreFromFavoriteUseCase
import com.bestcoders.zaheed.domain.use_case.UnFollowStoreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoreDetailsViewModel @Inject constructor(
    private val getStoreDetailsUseCase: GetStoreDetailsUseCase,
    private val followStoreUseCase: FollowStoreUseCase,
    private val unFollowStoreUseCase: UnFollowStoreUseCase,
    private val addStoreToFavoriteUseCase: AddStoreToFavoriteUseCase,
    private val removeStoreFromFavoriteUseCase: RemoveStoreFromFavoriteUseCase,
) : ViewModel() {

    var state = mutableStateOf(StoreDetailsState())
        private set

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private fun getStoreDetails(storeId: Int, page: Int) {
        getStoreDetailsUseCase(
            storeId = storeId,
            latitude = state.value.userLatitude.toString(),
            longitude = state.value.userLongitude.toString(),
            page = page
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    state.value = state.value.copy(
                        isLoading = false,
                        getStoreDetailsSuccess = true,
                        storeDetails = result.data,
                        allProducts = state.value.allProducts.plus(
                            result.data?.all?.items ?: mutableStateListOf()
                        ).toMutableStateList(),
                        paginationMeta = result.data?.all?.paginationMeta,
                    )
                }

                is Resource.Error -> {
                    state.value = state.value.copy(
                        isLoading = false,
                        getStoreDetailsSuccess = false,
                        error = result.message,
                    )
                }

                is Resource.Loading -> {
                    state.value = state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun followStore(storeId: Int) {
        followStoreUseCase(
            storeId = storeId,
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    state.value = state.value.copy(
                        isLoading = false,
                        followStoreSuccess = true,
                    )
                }

                is Resource.Error -> {
                    state.value = state.value.copy(
                        isLoading = false,
                        followStoreSuccess = false,
                        error = result.message,
                    )
                }

                is Resource.Loading -> {}
            }
        }.launchIn(viewModelScope)
    }

    private fun unFollowStore(storeId: Int) {
        unFollowStoreUseCase(
            storeId = storeId,
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    state.value = state.value.copy(
                        isLoading = false,
                        unfollowStoreSuccess = true,
                    )
                }

                is Resource.Error -> {
                    state.value = state.value.copy(
                        isLoading = false,
                        unfollowStoreSuccess = false,
                        error = result.message,
                    )
                }

                is Resource.Loading -> {}
            }
        }.launchIn(viewModelScope)
    }

    private fun addStoreToFavorite(storeId: Int) {
        if (Constants.userToken.isNotEmpty()) {
            addStoreToFavoriteUseCase(
                storeId = storeId,
                userToken = Constants.userToken
            ).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        state.value = state.value.copy(
                            isLoading = false,
                            isStoreAddedToFavorite = true,
                            error = null,
                        )
                    }

                    is Resource.Error -> {
                        state.value = state.value.copy(
                            isLoading = false,
                            isStoreAddedToFavorite = false,
                            error = result.message
                        )
                    }

                    is Resource.Loading -> {}
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun removeStoreFromFavorite(storeId: Int) {
        if (Constants.userToken.isNotEmpty()) {
            removeStoreFromFavoriteUseCase(
                storeId = storeId,
                userToken = Constants.userToken
            ).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        state.value = state.value.copy(
                            isLoading = false,
                            isStoreRemovedFromFavorite = true,
                            error = null,
                        )
                    }

                    is Resource.Error -> {
                        state.value = state.value.copy(
                            isLoading = false,
                            isStoreRemovedFromFavorite = false,
                            error = result.message
                        )
                    }

                    is Resource.Loading -> {}
                }
            }.launchIn(viewModelScope)
        }
    }

    fun resetState(newState: StoreDetailsState? = null) {
        state.value = newState ?: StoreDetailsState()
    }

    fun onEvent(event: StoreDetailsEvent) {
        when (event) {
            is StoreDetailsEvent.GetStoreDetails -> {
                resetState(
                    state.value.copy(
                        storeDetails = null,
                        allProducts = mutableStateListOf()
                    )
                )
                getStoreDetails(storeId = event.storeId, page = event.page)
            }

            is StoreDetailsEvent.LoadNextData -> {
                getStoreDetails(storeId = event.storeId, page = event.page)
            }

            is StoreDetailsEvent.OnFavoriteStoreClick -> {
                if (event.isFavorite) {
                    removeStoreFromFavorite(event.storeId)
                } else {
                    addStoreToFavorite(event.storeId)
                }
            }

            is StoreDetailsEvent.FollowStore -> {
                followStore(storeId = event.storeId)
            }

            is StoreDetailsEvent.UnFollowStore -> {
                unFollowStore(storeId = event.storeId)
            }

            is StoreDetailsEvent.ShowSnackBar -> {
                viewModelScope.launch {
                    _uiEvent.emit(UiEvent.ShowSnackbar(message = event.message))
                }
            }
        }
    }

}