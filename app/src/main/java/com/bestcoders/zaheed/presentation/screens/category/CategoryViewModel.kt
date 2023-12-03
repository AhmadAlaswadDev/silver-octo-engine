package com.bestcoders.zaheed.presentation.screens.category

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.core.util.Resource
import com.bestcoders.zaheed.core.util.UiEvent
import com.bestcoders.zaheed.domain.model.products.HomeLayout
import com.bestcoders.zaheed.domain.use_case.AddProductToFavoriteUseCase
import com.bestcoders.zaheed.domain.use_case.GetMainCategoryUseCase
import com.bestcoders.zaheed.domain.use_case.RemoveProductFromFavoriteUseCase
import com.bestcoders.zaheed.presentation.screens.home_container.HomeContainerViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getMainCategoryUseCase: GetMainCategoryUseCase,
    private val addProductToFavoriteUseCase: AddProductToFavoriteUseCase,
    private val removeProductFromFavoriteUseCase: RemoveProductFromFavoriteUseCase,
) : ViewModel() {

    var state = mutableStateOf(CategoryState())
        private set

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()


    private fun getMainCategory(
        categoryId: Int,
        page: Int,
        priceRangeMax: String = Constants.settings.layoutsFiltersDefaultMaxPrice,
        priceRangeMin: String = Constants.settings.layoutsFiltersDefaultMinPrice,
        sortBy: String = Constants.settings.layoutsFiltersDefaultSortBy,
        amountOfDiscount: String = Constants.settings.layoutsFiltersDefaultDiscount
    ) {
        getMainCategoryUseCase(
            userToken = state.value.userToken,
            page = page,
            categoryId = categoryId,
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
                        isLoading = false,
                        success = true,
                        endReached = result.data?.nearbyStores.isNullOrEmpty(),
                        homeLayoutList = state.value.homeLayoutList.plus(
                            HomeLayout(
                                nearbyStores = result.data?.nearbyStores,
                                bestSales = result.data?.bestSales,
                                categories = result.data?.categories,
                                homeBanner = result.data?.homeBanner,
                                paginationMeta = result.data?.paginationMeta,
                            )
                        ).toMutableStateList(),
                    )
                }

                is Resource.Error -> {
                    state.value = state.value.copy(
                        isLoading = false,
                        success = false,
                        error = result.message.toString(),
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

    fun resetState(newState: CategoryState?) {
        state.value = newState ?: CategoryState()
    }

    fun onEvent(event: CategoryEvent) {
        when (event) {
            is CategoryEvent.GetCategory -> {
                resetState(state.value.copy(homeLayoutList = mutableStateListOf()))
                getMainCategory(
                    categoryId = event.categoryId,
                    page = event.page,
                    priceRangeMax = event.priceRangeMax,
                    priceRangeMin = event.priceRangeMin,
                    sortBy = event.sortBy,
                    amountOfDiscount = event.amountOfDiscount,
                )
            }

            is CategoryEvent.LoadNextItems -> {
                getMainCategory(
                    categoryId = event.categoryId,
                    page = event.page,
                    priceRangeMax = event.priceRangeMax,
                    priceRangeMin = event.priceRangeMin,
                    sortBy = event.sortBy,
                    amountOfDiscount = event.amountOfDiscount,
                )
            }

            is CategoryEvent.ShowSnackBar -> {
                viewModelScope.launch {
                    _uiEvent.emit(UiEvent.ShowSnackbar(message = event.message, action = event.action))
                }
            }

            is CategoryEvent.OnFavoriteProductClick -> {
                if (event.isFavorite) {
                    removeProductFromFavorite(productId = event.productId)
                } else {
                    addProductToFavorite(productId = event.productId)
                }
            }
        }
    }

}