package com.bestcoders.zaheed.presentation.screens.search


import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bestcoders.zaheed.core.util.Resource
import com.bestcoders.zaheed.core.util.UiEvent
import com.bestcoders.zaheed.data.local.entity.UserAuthDataStoreEntity
import com.bestcoders.zaheed.domain.use_case.AddProductToFavoriteUseCase
import com.bestcoders.zaheed.domain.use_case.AddStoreToFavoriteUseCase
import com.bestcoders.zaheed.domain.use_case.GetSearchedProductsUseCase
import com.bestcoders.zaheed.domain.use_case.GetSearchedStoresUseCase
import com.bestcoders.zaheed.domain.use_case.RemoveProductFromFavoriteUseCase
import com.bestcoders.zaheed.domain.use_case.RemoveStoreFromFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchedStoresUseCase: GetSearchedStoresUseCase,
    private val getSearchedProductsUseCase: GetSearchedProductsUseCase,
    private val addProductToFavoriteUseCase: AddProductToFavoriteUseCase,
    private val removeProductFromFavoriteUseCase: RemoveProductFromFavoriteUseCase,
    private val addStoreToFavoriteUseCase: AddStoreToFavoriteUseCase,
    private val removeStoreFromFavoriteUseCase: RemoveStoreFromFavoriteUseCase,
    private val dataStore: DataStore<UserAuthDataStoreEntity>
) : ViewModel() {

    var state = mutableStateOf(SearchState())
        private set

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        getUserToken()
    }

    private fun getSearchedStores(page: Int, storeName: String) {
        getSearchedStoresUseCase(
            userToken = state.value.userToken,
            page = page,
            latitude = state.value.userLatitude.toString(),
            longitude = state.value.userLongitude.toString(),
            storeName = storeName
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

    private fun getSearchedProducts(page: Int, productName: String) {
        getSearchedProductsUseCase(
            userToken = state.value.userToken,
            page = page,
            latitude = state.value.userLatitude.toString(),
            longitude = state.value.userLongitude.toString(),
            productName = productName
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
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

    private fun addProductToFavorite(productId: Int) {
        if (state.value.userToken.isNotEmpty()) {
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

                    is Resource.Loading -> {}
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun removeProductFromFavorite(productId: Int) {
        if (state.value.userToken.isNotEmpty()) {
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

                    is Resource.Loading -> {}
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun addStoreToFavorite(storeId: Int) {
        if (state.value.userToken.isNotEmpty()) {
            addStoreToFavoriteUseCase(
                storeId = storeId,
                userToken = state.value.userToken
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
        if (state.value.userToken.isNotEmpty()) {
            removeStoreFromFavoriteUseCase(
                storeId = storeId,
                userToken = state.value.userToken
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

    private fun getUserToken() {
        viewModelScope.launch {
            dataStore.data.collectLatest {
                state.value = state.value.copy(userToken = it.userToken ?: "")
            }
        }
    }

    fun resetState(newState: SearchState?) {
        state.value = newState ?: SearchState()
    }

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.OnFavoriteProductClick -> {
                if (event.isFavorite) {
                    removeProductFromFavorite(productId = event.productId)
                } else {
                    addProductToFavorite(productId = event.productId)
                }
            }

            is SearchEvent.OnFavoriteStoreClick -> {
                if (event.isFavorite) {
                    removeStoreFromFavorite(storeId = event.storeId)
                } else {
                    addStoreToFavorite(storeId = event.storeId)
                }
            }

            is SearchEvent.GetSearchedStores -> {
                resetState(state.value.copy(stores = mutableStateListOf(), paginationMetaStores = null))
                getSearchedStores(page = event.page, storeName = event.storeName)
            }

            is SearchEvent.LoadNextItemsStores -> {
                getSearchedStores(page = event.page, storeName = event.storeName)
            }

            is SearchEvent.GetSearchedProducts -> {
                resetState(state.value.copy(storesWithProducts = mutableStateListOf(), paginationMetaProducts = null))
                getSearchedProducts(page = event.page, productName = event.productName)
            }

            is SearchEvent.LoadNextItemsProducts -> {
                getSearchedProducts(page = event.page, productName = event.productName)
            }

            is SearchEvent.ShowSnackBar -> {
                viewModelScope.launch {
                    _uiEvent.emit(
                        UiEvent.ShowSnackbar(
                            message = event.message,
                            action = event.action
                        )
                    )
                }
            }
        }
    }

}