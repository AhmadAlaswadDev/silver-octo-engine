package com.bestcoders.zaheed.presentation.screens.favorite

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.core.util.Resource
import com.bestcoders.zaheed.core.util.UiEvent
import com.bestcoders.zaheed.domain.use_case.AddProductToFavoriteUseCase
import com.bestcoders.zaheed.domain.use_case.AddStoreToFavoriteUseCase
import com.bestcoders.zaheed.domain.use_case.GetFavoriteProductsUseCase
import com.bestcoders.zaheed.domain.use_case.GetFavoriteStoresUseCase
import com.bestcoders.zaheed.domain.use_case.RemoveProductFromFavoriteUseCase
import com.bestcoders.zaheed.domain.use_case.RemoveStoreFromFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavoriteStoresUseCase: GetFavoriteStoresUseCase,
    private val getFavoriteProductsUseCase: GetFavoriteProductsUseCase,
    private val addProductToFavoriteUseCase: AddProductToFavoriteUseCase,
    private val removeProductFromFavoriteUseCase: RemoveProductFromFavoriteUseCase,
    private val addStoreToFavoriteUseCase: AddStoreToFavoriteUseCase,
    private val removeStoreFromFavoriteUseCase: RemoveStoreFromFavoriteUseCase,
) : ViewModel() {

    var state = mutableStateOf(FavoriteState())
        private set

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private fun getFavoriteStores(page: Int) {
        if (Constants.userToken.isNotEmpty()) {
            getFavoriteStoresUseCase(
                userToken = Constants.userToken,
                page = page,
                latitude = state.value.userLatitude.toString(),
                longitude = state.value.userLongitude.toString(),
            ).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        state.value = state.value.copy(
                            stores = state.value.stores.plus(
                                result.data?.storeWithProducts ?: mutableStateListOf()
                            ).toMutableStateList(),
                            pageStores = page,
                            endReachedStores = result.data?.storeWithProducts.isNullOrEmpty(),
                            isLoading = false,
                            success = true,
                            paginationMetaStores = result.data?.paginationMeta,
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
    }

    private fun getFavoriteProducts(page: Int) {
        if (Constants.userToken.isNotEmpty()) {
            getFavoriteProductsUseCase(
                userToken = Constants.userToken,
                page = page,
                latitude = state.value.userLatitude.toString(),
                longitude = state.value.userLongitude.toString(),
            ).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        Log.e(
                            "AASDASDASDAS",
                            "getFavoriteProducts: ${result.data?.paginationMeta?.currentPage.toString()}",
                        )
                        state.value = state.value.copy(
                            storesWithProducts = state.value.storesWithProducts.plus(
                                result.data?.storeWithProducts ?: mutableStateListOf()
                            ).toMutableStateList(),
                            pageProducts = page,
                            endReachedProducts = result.data?.storeWithProducts.isNullOrEmpty(),
                            isLoading = false,
                            success = true,
                            paginationMetaProducts = result.data?.paginationMeta,
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
    }

    private fun addProductToFavorite(productId: Int) {
        if (Constants.userToken.isNotEmpty()) {
            addProductToFavoriteUseCase(
                productId = productId,
                userToken = Constants.userToken
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
        if (Constants.userToken.isNotEmpty()) {
            removeProductFromFavoriteUseCase(
                productId = productId,
                userToken = Constants.userToken
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

                    is Resource.Loading -> {
//                    state.value = state.value.copy(isLoading = true)
                    }
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

                    is Resource.Loading -> {
//                    state.value = state.value.copy(isLoading = true)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }


    fun resetState(newState: FavoriteState?) {
        state.value = newState ?: FavoriteState()
    }

    fun onEvent(event: FavoriteEvent) {
        when (event) {
            is FavoriteEvent.OnFavoriteProductClick -> {
                if (event.isFavorite) {
                    removeProductFromFavorite(event.productId)
                } else {
                    addProductToFavorite(event.productId)
                }
            }

            is FavoriteEvent.OnFavoriteStoreClick -> {
                if (event.isFavorite) {
                    removeStoreFromFavorite(event.storeId)
                } else {
                    addStoreToFavorite(event.storeId)
                }
            }

            is FavoriteEvent.LoadNextItemsProducts -> {
                getFavoriteProducts(event.page)
            }

            is FavoriteEvent.LoadNextItemsStores -> {
                getFavoriteStores(event.page)
            }

            is FavoriteEvent.ShowSnackBar -> {
                viewModelScope.launch {
                    _uiEvent.emit(UiEvent.ShowSnackbar(message = event.message))
                }
            }

            is FavoriteEvent.GetFavoriteProducts -> {
                getFavoriteProducts(event.page)
            }

            is FavoriteEvent.GetFavoriteStores -> {
                getFavoriteStores(event.page)
            }
        }
    }

}