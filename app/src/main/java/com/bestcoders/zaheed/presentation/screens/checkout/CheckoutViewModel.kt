package com.bestcoders.zaheed.presentation.screens.checkout


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bestcoders.zaheed.core.util.Resource
import com.bestcoders.zaheed.core.util.UiEvent
import com.bestcoders.zaheed.domain.use_case.ChangeProductQuantityUseCase
import com.bestcoders.zaheed.domain.use_case.GetCartByStoreUseCase
import com.bestcoders.zaheed.domain.use_case.RemoveProductFromCartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val getCartByStoreUseCase: GetCartByStoreUseCase,
    private val changeProductQuantityUseCase: ChangeProductQuantityUseCase,
    private val removeProductFromCartUseCase: RemoveProductFromCartUseCase,
) : ViewModel() {

    var state = mutableStateOf(CheckoutState())
        private set

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private fun getCartByStore(storeId: Int) {
        getCartByStoreUseCase(
            latitude = state.value.userLatitude.toString(),
            longitude = state.value.userLongitude.toString(),
            storeId = storeId
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    state.value = state.value.copy(
                        isLoading = false,
                        getCartByStoreSuccess = true,
                        cartByStoreModel = result.data
                    )
                }

                is Resource.Error -> {
                    state.value = state.value.copy(
                        isLoading = false,
                        getCartByStoreSuccess = false,
                        error = result.message,
                    )
                }

                is Resource.Loading -> {
                    state.value = state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun changeProductQuantity(
        productId: String,
        variant: String,
        quantity: String
    ) {
        changeProductQuantityUseCase(
            productId = productId,
            variant = variant,
            quantity = quantity
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    state.value = state.value.copy(
                        isLoading = false,
                        changeProductQuantitySuccess = true,
                        error = null,
                    )
                }

                is Resource.Error -> {
                    state.value = state.value.copy(
                        isLoading = false,
                        changeProductQuantitySuccess = false,
                        error = result.message,
                    )
                }

                is Resource.Loading -> {}
            }
        }.launchIn(viewModelScope)
    }

    private fun removeProductFromCart(
        productId: Int,
        variant: String,
    ) {
        removeProductFromCartUseCase(
            productId = productId,
            variant = variant
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    state.value.cartByStoreModel!!.cart.products.removeIf {
                        it.id == productId && it.variant == variant
                    }
                    val updatedList =
                        state.value.cartByStoreModel!!.cart.products.filterNot {
                            it.id == productId && it.variant == variant
                        }
                    state.value.cartByStoreModel!!.cart.products.clear()
                    state.value.cartByStoreModel!!.cart.products.addAll(updatedList)

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

                is Resource.Loading -> {}
            }
        }.launchIn(viewModelScope)
    }

    fun resetState(newState: CheckoutState? = null) {
        state.value = newState ?: CheckoutState()
    }

    fun onEvent(event: CheckoutEvent) {
        when (event) {
            is CheckoutEvent.GetCartByStore -> {
                getCartByStore(storeId = event.storeId)
            }

            is CheckoutEvent.ChangeProductQuantity -> {
                changeProductQuantity(
                    productId = event.productId,
                    variant = event.variant,
                    quantity = event.quantity.toString(),
                )
            }

            is CheckoutEvent.RemoveProductFromCart -> {
                removeProductFromCart(
                    productId = event.productId.toInt(),
                    variant = event.variant,
                )
            }

            is CheckoutEvent.ShowSnackBar -> {
                viewModelScope.launch {
                    _uiEvent.emit(UiEvent.ShowSnackbar(event.message))
                }
            }
        }
    }

}