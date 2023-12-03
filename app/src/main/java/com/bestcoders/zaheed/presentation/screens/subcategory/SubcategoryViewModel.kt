package com.bestcoders.zaheed.presentation.screens.subcategory

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.core.util.Resource
import com.bestcoders.zaheed.core.util.UiEvent
import com.bestcoders.zaheed.domain.use_case.AddProductToFavoriteUseCase
import com.bestcoders.zaheed.domain.use_case.GetSubcategoryUseCase
import com.bestcoders.zaheed.domain.use_case.RemoveProductFromFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubcategoryViewModel @Inject constructor(
    private val getSubcategoryUseCase: GetSubcategoryUseCase,
    private val addProductToFavoriteUseCase: AddProductToFavoriteUseCase,
    private val removeProductFromFavoriteUseCase: RemoveProductFromFavoriteUseCase,
) : ViewModel() {

    var state = mutableStateOf(SubcategoryState())
        private set

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private fun getSubcategory(
        subcategoryId: Int,
        page: Int,
        priceRangeMax: String = Constants.settings.layoutsFiltersDefaultMaxPrice,
        priceRangeMin: String = Constants.settings.layoutsFiltersDefaultMinPrice,
        sortBy: String = Constants.settings.layoutsFiltersDefaultSortBy,
        amountOfDiscount: String = Constants.settings.layoutsFiltersDefaultDiscount
    ) {
        getSubcategoryUseCase(
            userToken = state.value.userToken,
            page = page,
            subcategoryId = subcategoryId,
            latitude = state.value.userLatitude.toString(),
            longitude = state.value.userLongitude.toString(),
            distance = Constants.settings.layoutsMaxDistanceLimit,
            amountOfDiscount = amountOfDiscount,
            priceRangeMin = priceRangeMin,
            priceRangeMax = priceRangeMax,
            sortBy = sortBy,
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    state.value = state.value.copy(
                        stores = state.value.stores.plus(
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
                    viewModelScope.launch {
                        _uiEvent.emit(
                            UiEvent.ShowSnackbar(
                                message = result.message.toString(), action = null
                            )
                        )
                    }
                }

                is Resource.Loading -> {
                    state.value = state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun addProductToFavorite(productId: Int) {
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

    private fun removeProductFromFavorite(productId: Int) {
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


    fun resetState(newState: SubcategoryState?) {
        state.value = newState ?: SubcategoryState()
    }

    fun onEvent(event: SubcategoryEvent) {
        when (event) {
            is SubcategoryEvent.GetSubcategory -> {
                resetState(state.value.copy(stores = mutableStateListOf()))
                getSubcategory(
                    subcategoryId = event.subcategoryId,
                    page = event.page,
                    priceRangeMax = event.priceRangeMax,
                    priceRangeMin = event.priceRangeMin,
                    sortBy = event.sortBy,
                    amountOfDiscount = event.amountOfDiscount,
                )
            }

            is SubcategoryEvent.LoadNextItems -> {
                getSubcategory(
                    subcategoryId = event.subcategoryId,
                    page = event.page,
                    priceRangeMax = event.priceRangeMax,
                    priceRangeMin = event.priceRangeMin,
                    sortBy = event.sortBy,
                    amountOfDiscount = event.amountOfDiscount,
                )
            }

            is SubcategoryEvent.ShowSnackBar -> {
                viewModelScope.launch {
                    _uiEvent.emit(
                        UiEvent.ShowSnackbar(
                            message = event.message,
                            action = event.action
                        )
                    )
                }
            }

            is SubcategoryEvent.OnFavoriteProductClick -> {
                if (event.isFavorite) {
                    removeProductFromFavorite(productId = event.productId)
                } else {
                    addProductToFavorite(productId = event.productId)
                }
            }
        }
    }

}