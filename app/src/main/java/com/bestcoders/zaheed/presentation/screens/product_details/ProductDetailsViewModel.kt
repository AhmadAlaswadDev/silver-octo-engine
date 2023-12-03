package com.bestcoders.zaheed.presentation.screens.product_details


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.core.util.Resource
import com.bestcoders.zaheed.core.util.UiEvent
import com.bestcoders.zaheed.domain.use_case.AddProductToCartUseCase
import com.bestcoders.zaheed.domain.use_case.AddProductToFavoriteUseCase
import com.bestcoders.zaheed.domain.use_case.GetProductDetailsUseCase
import com.bestcoders.zaheed.domain.use_case.GetProductVariationDetailsUseCase
import com.bestcoders.zaheed.domain.use_case.RemoveProductFromFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val getProductDetailsUseCase: GetProductDetailsUseCase,
    private val getProductVariationDetailsUseCase: GetProductVariationDetailsUseCase,
    private val addProductToCartUseCase: AddProductToCartUseCase,
    private val addProductToFavoriteUseCase: AddProductToFavoriteUseCase,
    private val removeProductFromFavoriteUseCase: RemoveProductFromFavoriteUseCase,
) : ViewModel() {

    var state = mutableStateOf(ProductDetailsState())
        private set

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private fun addProductToCart(
        productId: Int,
        variant: String,
        quantity: Int
    ) {
        addProductToCartUseCase(
            productId = productId,
            variant = variant,
            quantity = quantity,
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    state.value = state.value.copy(
                        isLoading = false,
                        addProductToCartSuccess = true,
                    )
                }

                is Resource.Error -> {
                    state.value = state.value.copy(
                        isLoading = false,
                        addProductToCartSuccess = false,
                        error = result.message,
                    )
                }

                is Resource.Loading -> {
                    state.value = state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getProductDetails(productId: Int) {
        getProductDetailsUseCase(
            userToken = Constants.userToken,
            latitude = state.value.userLatitude.toString(),
            longitude = state.value.userLongitude.toString(),
            productId = productId
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    state.value = state.value.copy(
                        isLoading = false,
                        getProductDetails = true,
                        productDetails = result.data,
                    )
                }

                is Resource.Error -> {
                    state.value = state.value.copy(
                        isLoading = false,
                        getProductDetails = false,
                        error = result.message,
                    )
                }

                is Resource.Loading -> {
                    state.value = state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getProductVariationDetails(productId: Int, variant: String) {
        getProductVariationDetailsUseCase(
            productId = productId,
            variant = variant,
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    state.value = state.value.copy(
                        isLoading = false,
                        getProductVariationDetailsSuccess = true,
                        productVariationDetails = result.data,
                    )
                }

                is Resource.Error -> {
                    state.value = state.value.copy(
                        isLoading = false,
                        getProductVariationDetailsSuccess = false,
                        productVariationDetails = null,
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
        if(Constants.userToken.isNotEmpty()){
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

                    is Resource.Loading -> {}
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun removeProductFromFavorite(productId: Int) {
        if(Constants.userToken.isNotEmpty()){
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

                    is Resource.Loading -> {}
                }
            }.launchIn(viewModelScope)
        }
    }

    fun resetState(newState: ProductDetailsState? = null) {
        state.value = newState ?: ProductDetailsState()
    }

    fun onEvent(event: ProductDetailsEvent) {
        when (event) {
            is ProductDetailsEvent.AddProductToCart -> {
                addProductToCart(
                    productId = event.productId,
                    variant = event.variant,
                    quantity = event.quantity,
                )
            }

            is ProductDetailsEvent.OnFavoriteProductClick -> {
                if (event.isFavorite) {
                    removeProductFromFavorite(event.productId)
                } else {
                    addProductToFavorite(event.productId)
                }
            }

            is ProductDetailsEvent.GetProductDetails -> {
                getProductDetails(productId = event.productId)
            }

            is ProductDetailsEvent.GetProductVariationDetails -> {
                getProductVariationDetails(
                    variant = event.variant,
                    productId = event.productId
                )
            }

            is ProductDetailsEvent.ShowSnackBar -> {
                viewModelScope.launch {
                    _uiEvent.emit(UiEvent.ShowSnackbar(message = event.message))
                }
            }
        }
    }

}