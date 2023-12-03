package com.bestcoders.zaheed.presentation.screens.home_container

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.core.util.Resource
import com.bestcoders.zaheed.core.util.UiEvent
import com.bestcoders.zaheed.data.local.entity.UserAuthDataStoreEntity
import com.bestcoders.zaheed.domain.model.products.HomeLayout
import com.bestcoders.zaheed.domain.use_case.GetHomeLayoutDataUseCase
import com.bestcoders.zaheed.domain.use_case.GetUserLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeContainerViewModel @Inject constructor(
    private val getUserLocationUseCase: GetUserLocationUseCase,
    private val dataStore: DataStore<UserAuthDataStoreEntity>,
    private val getHomeLayoutDataUseCase: GetHomeLayoutDataUseCase
) : ViewModel() {

    var state = mutableStateOf(HomeContainerState())
        private set

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()


    init {
        getUserToken()
    }

    private fun getHomeLayoutData(
        page: Int,
        priceRangeMax: String = Constants.settings.layoutsFiltersDefaultMaxPrice,
        priceRangeMin: String = Constants.settings.layoutsFiltersDefaultMinPrice,
        sortBy: String = Constants.settings.layoutsFiltersDefaultSortBy,
        amountOfDiscount: String = Constants.settings.layoutsFiltersDefaultDiscount
    ) {
        getHomeLayoutDataUseCase(
            userToken = state.value.userToken,
            page = page,
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

    private fun getUserLocation() {
        getUserLocationUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val userLocation = result.data
                    state.value = state.value.copy(
                        userLocationSuccess = true,
                        userLatitude = userLocation!!.latitude,
                        userLongitude = userLocation.longitude,
                    )
                    getHomeLayoutData(1)
                }

                is Resource.Error -> {
                    state.value = state.value.copy(
                        userLocationSuccess = false,
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

                is Resource.Loading -> {}
            }
        }.launchIn(viewModelScope)
    }

    private fun getUserToken() {
        viewModelScope.launch {
            dataStore.data.collectLatest {
                state.value = state.value.copy(userToken = it.userToken ?: "")
            }
        }
    }

    fun resetState(newState: HomeContainerState? = null) {
        state.value = newState ?: HomeContainerState()
    }

    fun onEvent(event: HomeContainerEvent) {
        when (event) {

            is HomeContainerEvent.ShowSnackBar -> {
                viewModelScope.launch {
                    _uiEvent.emit(
                        UiEvent.ShowSnackbar(
                            message = event.message,
                            action = event.action
                        )
                    )
                }
            }

            is HomeContainerEvent.GetUserLocationWithHomeData -> {
                getUserLocation()
            }

            is HomeContainerEvent.LoadNextItems -> {
                Log.e("ASLDFJASI", "HomeContainerNavGraph: ${event.page.toString()}", )
                getHomeLayoutData(
                    page = event.page,
                    priceRangeMax = event.priceRangeMax,
                    priceRangeMin = event.priceRangeMin,
                    sortBy = event.sortBy,
                    amountOfDiscount = event.amountOfDiscount,
                )
            }

            is HomeContainerEvent.GetHomeLayoutWithCustomLocation -> {
                resetState(
                    state.value.copy(
                        userLatitude = event.latitude,
                        userLongitude = event.longitude,
                        homeLayoutList = mutableStateListOf()
                    )
                )
                getHomeLayoutData(1)
            }

            is HomeContainerEvent.GetHomeLayoutWithFilter -> {
                resetState(
                    state.value.copy(
                        isLoading = false,
                        success = false,
                        endReached = false,
                    ),
                )
                getHomeLayoutData(
                    page = event.page,
                    priceRangeMax = event.priceRangeMax,
                    priceRangeMin = event.priceRangeMin,
                    sortBy = event.sortBy,
                    amountOfDiscount = event.amountOfDiscount,
                )
            }
        }
    }

}