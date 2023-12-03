package com.bestcoders.zaheed.presentation.screens.cart


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bestcoders.zaheed.core.util.Resource
import com.bestcoders.zaheed.core.util.UiEvent
import com.bestcoders.zaheed.domain.use_case.AddProductToCartUseCase
import com.bestcoders.zaheed.domain.use_case.GetCartsUseCase
import com.bestcoders.zaheed.domain.use_case.RemoveProductFromCartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val addProductToCartUseCase: AddProductToCartUseCase,
    private val removeProductFromCartUseCase: RemoveProductFromCartUseCase,
    private val getCartsUseCase: GetCartsUseCase,
) : ViewModel() {

    var state = mutableStateOf(CartState())
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
            quantity = quantity
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

    private fun removeProductFromCart(
        productId: Int,
        variant: String,
    ) {
        removeProductFromCartUseCase(
            productId = productId,
            variant = variant,
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    state.value = state.value.copy(
                        isLoading = false,
                        removeProductFromCartSuccess = true,
                    )
                }

                is Resource.Error -> {
                    state.value = state.value.copy(
                        isLoading = false,
                        removeProductFromCartSuccess = false,
                        error = result.message,
                    )
                }

                is Resource.Loading -> {
                    state.value = state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }



    private fun getCarts() {
        getCartsUseCase(
            latitude = state.value.userLatitude.toString(),
            longitude = state.value.userLongitude.toString(),
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    state.value = state.value.copy(
                        isLoading = false,
                        getCartsSuccess = true,
                        cartModel = result.data
                    )
                }

                is Resource.Error -> {
                    state.value = state.value.copy(
                        isLoading = false,
                        getCartsSuccess = false,
                        error = result.message,
                    )
                }

                is Resource.Loading -> {
                    state.value = state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }


    fun resetState(newState: CartState? = null) {
        state.value = newState ?: CartState()
    }

    fun onEvent(event: CartEvent) {
        when (event) {
            is CartEvent.AddProductToCart -> {
                addProductToCart(
                    productId = event.productId,
                    variant = event.variant,
                    quantity = event.quantity,
                )
            }

            is CartEvent.RemoveProductFromCart -> {
                removeProductFromCart(
                    productId = event.productId,
                    variant = event.variant,
                )
            }



            is CartEvent.GetCarts -> {
                getCarts()
            }

            is CartEvent.ShowSnackBar -> {
                viewModelScope.launch {
                    _uiEvent.emit(UiEvent.ShowSnackbar(event.message))
                }
            }
        }
    }

}