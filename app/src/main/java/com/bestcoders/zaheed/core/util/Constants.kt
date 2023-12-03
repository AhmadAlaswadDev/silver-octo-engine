package com.bestcoders.zaheed.core.util

import com.bestcoders.zaheed.domain.model.settings.Settings

object Constants {
    const val EXCEPTION_TAG = "ZaheedException"
    const val DATA_STORE_FILE_NAME = "user_auth.json"
    const val N_V_U = "n_v_u"
    const val PHONE_NUMBER_LENGTH = 9
    const val FULL_PHONE_NUMBER = 13
    const val VERIFICATION_CODE_LENGTH = 6
    const val PASSWORD_LENGTH = 6
    const val REQUEST_CHECK_SETTINGS = 123
    const val MALE = "male"
    const val FEMALE = "female"
    var DEFAULT_LANGUAGE = "sa"
    var ARABIC_LANGUAGE_CODE = "ar"
    var ENGLISH_LANGUAGE_CODE = "en"
    var SAUDI_LANGUAGE_CODE = "sa"
    const val RESEND_OTP_TIME = 60
    const val COUNTRY_CODE = "+966"
    const val CORNER_RADUIES = 8
    var MAIN_CURENCY = " SR"
    const val ANIMATION_DURATION = 250
    const val ONLINE_PAYMENT = "telr"
    const val PICKUP_TIME = "now"
    const val BEARER_TOKEN = "Bearer "
    lateinit var settings: Settings
    lateinit var userToken: String
    lateinit var tempToken: String
    lateinit var userName: String
    lateinit var userPhone: String
    lateinit var userEmail: String
    lateinit var userGender: String
    lateinit var userBirthDate: String
    lateinit var userSaved: String

    fun removeUserData(){
        userToken = ""
        tempToken = ""
        userName = ""
        userPhone = ""
        userEmail = ""
        userGender = ""
        userBirthDate = ""
        userSaved = ""
    }

}
/*
package com.bestcoders.zaheed.presentation.screens.home.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.bestcoders.zaheed.data.remote.model.products.PaginationMeta
import com.bestcoders.zaheed.domain.model.products.BestSale
import com.bestcoders.zaheed.domain.model.products.Category
import com.bestcoders.zaheed.domain.model.products.HomeBanner
import com.bestcoders.zaheed.presentation.components.BannerWithAutomaticImageSwitching
import com.bestcoders.zaheed.presentation.components.BestDealsSection
import com.bestcoders.zaheed.presentation.components.CategoriesSection
import com.bestcoders.zaheed.presentation.components.PaginationLoader
import com.bestcoders.zaheed.presentation.components.StoreWithProductsComponent
import com.bestcoders.zaheed.presentation.screens.home_container.BestSalesAndNearbyStores

@Composable
fun HomeContent(
    padding: PaddingValues,
    homePadding: PaddingValues,
    homeBanners: List<HomeBanner>? = null,
    categories: List<Category>,
    bestSalesAndNearbyStores: List<BestSalesAndNearbyStores>,
    loadNextItems: (Int) -> Unit,
    isNearbyLoading: Boolean = false,
    nearbyEndReached: Boolean = false,
    onFavoriteClick: (productId: Int, isFavorite: Boolean) -> Unit,
    onCategoryClick: (categoryId: Int, categoryName: String) -> Unit,
    paginationMeta: PaginationMeta?,
    onProductClick: (Int) -> Unit
) {
    val displayedStoreKeys = mutableSetOf<Any>()
    val displayedBestSalesSet = mutableSetOf<BestSale>()

    val listState = rememberLazyListState()

    val homeBannersValue by remember {
        derivedStateOf { homeBanners }
    }
    val categoriesValue by remember {
        derivedStateOf { categories }
    }

    val bestSalesAndNearbyStoresValue by remember {
        derivedStateOf { bestSalesAndNearbyStores }
    }
    val uniqueBestSalesList by
    remember {
        derivedStateOf {
            bestSalesAndNearbyStoresValue[0].bestSales.filter { bestSale ->
                displayedBestSalesSet.add(bestSale)
            }
        }
    }
    val uniqueNearbyStoresList by
    remember {
        derivedStateOf {
            bestSalesAndNearbyStoresValue[0].nearbyStores.filter { storeFilter ->
                displayedStoreKeys.add(storeFilter.id)
            }
        }
    }
    LazyColumn(
        modifier = Modifier
            .zIndex(1f)
            .fillMaxSize()
            .imePadding()
            .padding(
                top = padding.calculateTopPadding(),
                bottom = homePadding.calculateBottomPadding()
            ),
        verticalArrangement = Arrangement.spacedBy(30.dp),
        state = listState,
        content = {
            if (!homeBannersValue.isNullOrEmpty()) {
                item {
                    BannerWithAutomaticImageSwitching { homeBannersValue!! }
                }
            }
            if (categoriesValue.isNotEmpty()) {
                item {
                    CategoriesSection(
                        list = categoriesValue,
                        onCategoryClick = onCategoryClick,
                    )
                }
            }

            if (bestSalesAndNearbyStoresValue.isNotEmpty()) {
                Log.e("asdglkasdgk", "HomeContent: ${bestSalesAndNearbyStoresValue[0]}")

                if (uniqueBestSalesList.isNotEmpty()) {
                    item {
                        BestDealsSection(
                            modifier = Modifier,
                            list = uniqueBestSalesList,
                            onFavoriteClick = { id, isFavorite ->
                                onFavoriteClick(id, isFavorite)
                            },
                            onProductClick = onProductClick
                        )
                    }

                    item {
                        NearbyStoresHeader()
                    }

                    if(uniqueNearbyStoresList.isNotEmpty()){
                        item {
                            uniqueNearbyStoresList.forEachIndexed { index, store ->
                                StoreWithProductsComponent(
                                    store = store,
                                    onFavoriteClick = { id, isFavorite ->
                                        onFavoriteClick(id, isFavorite)
                                    },
                                    onProductClick = onProductClick
                                )
                            }
                        }
                    }
                }
            }
            if (isNearbyLoading) {
                item {
                    PaginationLoader()
                }
            }

        }
    )
    LaunchedEffect(listState.layoutInfo) {
        val totalItems = listState.layoutInfo.totalItemsCount
        val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index

        if (
            lastVisibleItem != null
            && paginationMeta?.nextPage != null
            && lastVisibleItem >= totalItems - 5
        ) {
            Log.e("ASFKHUASF", "HomeContent: ${paginationMeta.nextPage}")
            loadNextItems(paginationMeta.nextPage)
        }
    }
}

 */
/*
package com.bestcoders.zaheed.presentation.screens.home_container

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.core.util.Resource
import com.bestcoders.zaheed.core.util.UiEvent
import com.bestcoders.zaheed.data.local.entity.UserAuthDataStoreEntity
import com.bestcoders.zaheed.domain.model.products.BestSale
import com.bestcoders.zaheed.domain.model.products.Product
import com.bestcoders.zaheed.domain.model.products.ShopCategory
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
        priceRangeMax: String = Constants.DEFAULT_MAX_PRICE_FILTER,
        priceRangeMin: String = Constants.DEFAULT_MIN_PRICE_FILTER,
        sortBy: String = Constants.DEFAULT_SORT_BY_FILTER,
        amountOfDiscount: String = Constants.DEFAULT_AMOUNT_OF_DISCOUNT
    ) {
        getHomeLayoutDataUseCase(
            userToken = state.value.userToken,
            page = page,
            latitude = state.value.userLatitude.toString(),
            longitude = state.value.userLongitude.toString(),
            distance = Constants.DEFAULT_DISTANCE,
            amountOfDiscount = amountOfDiscount,
            priceRangeMin = priceRangeMin,
            priceRangeMax = priceRangeMax,
            sortBy = sortBy,
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    state.value = state.value.copy(
                        page = page,
                        endReached = result.data?.nearbyStores.isNullOrEmpty(),
                        isLoading = false,
                        success = true,
                        homeBanner = state.value.homeBanner.plus(result.data?.homeBanner ?: mutableStateListOf()).toMutableStateList(),
                        categories = state.value.categories.plus(result.data?.categories ?: mutableStateListOf()).toMutableStateList(),
                        paginationMeta = result.data?.paginationMeta,
                        bestSalesAndNearbyStores = state.value.bestSalesAndNearbyStores.plusElement(
                            BestSalesAndNearbyStores(
                                bestSales = result.data?.bestSales ?: mutableStateListOf(),
                                nearbyStores = result.data?.nearbyStores ?: mutableStateListOf()
                            )
                        ).toMutableStateList(),
                    )
                }

                is Resource.Error -> {
                    state.value = state.value.copy(
                        isLoading = false,
                        success = false,
                        error = result.message ?: "Something went wrong!",
                    )
                    viewModelScope.launch {
                        _uiEvent.emit(
                            UiEvent.ShowSnackbar(
                                message = result.message ?: "Something went wrong!", action = null
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
                        error = result.message ?: "Something went wrong!",
                    )
                    viewModelScope.launch {
                        _uiEvent.emit(
                            UiEvent.ShowSnackbar(
                                message = result.message ?: "Something went wrong!", action = null
                            )
                        )
                    }
                }

                is Resource.Loading -> {
//                    state.value = state.value.copy(isLoading = true)
                }
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
                    _uiEvent.emit(UiEvent.ShowSnackbar(message = event.message, action = event.action))
                }
            }

            is HomeContainerEvent.GetUserLocationWithHomeData -> {
                getUserLocation()
            }

            is HomeContainerEvent.LoadNextItems -> {
                getHomeLayoutData(page = event.page)
            }

            is HomeContainerEvent.GetHomeLayoutWithCustomLocation -> {
                resetState(
                    state.value.copy(
                        userLatitude = event.latitude,
                        userLongitude = event.longitude,
                        bestSalesAndNearbyStores = mutableStateListOf()
                    )
                )
                getHomeLayoutData(1)
            }

            is HomeContainerEvent.GetHomeLayoutWithFilter -> {
                resetState(
                    state.value.copy(
                        endReached = false,
                        isLoading = false,
                        success = false,
                        homeBanner = mutableStateListOf(),
                        categories = mutableStateListOf(),
                        bestSalesAndNearbyStores = mutableStateListOf(),
                        paginationMeta = null,
                    ),
                )
                getHomeLayoutData(
                    page = 1,
                    priceRangeMax = event.priceRangeMax,
                    priceRangeMin = event.priceRangeMin,
                    sortBy = event.sortBy,
                    amountOfDiscount = event.amountOfDiscount,
                )
            }
        }
    }

    /**
     * For testing new way in home
     */

    data class HomeDataNew(
        val id: Int,
        val address: String,
        val distance: Double,
        var isFavorite: Boolean,
        val logo: String,
        val name: String,
        val rating: Int,
        val saved: String,
        val category: ShopCategory?,
        val products: SnapshotStateList<Product>?,
        val bestSales: SnapshotStateList<BestSale>? = null
    )

}
 */
/*
package com.bestcoders.zaheed.presentation.screens.home.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.zIndex
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.productDetailsDataResponse.remote.model.products.PaginationMeta
import com.bestcoders.zaheed.domain.model.products.BestSale
import com.bestcoders.zaheed.domain.model.products.CategoryProductDetailsResponse
import com.bestcoders.zaheed.domain.model.products.HomeBanner
import com.bestcoders.zaheed.presentation.components.BannerWithAutomaticImageSwitching
import com.bestcoders.zaheed.presentation.components.BestDealsSection
import com.bestcoders.zaheed.presentation.components.CategoriesSection
import com.bestcoders.zaheed.presentation.components.PaginationLoader
import com.bestcoders.zaheed.presentation.components.SpacerHeightExtraLarge
import com.bestcoders.zaheed.presentation.components.SpacerHeightLarge
import com.bestcoders.zaheed.presentation.components.SpacerHeightMedium
import com.bestcoders.zaheed.presentation.components.SpacerHeightSmall
import com.bestcoders.zaheed.presentation.components.StoreWithProductsComponent
import com.bestcoders.zaheed.presentation.screens.home_container.BestSalesAndNearbyStores

@Composable
fun HomeContent(
    padding: PaddingValues,
    homeBanners: List<HomeBanner>? = null,
    categories: List<CategoryProductDetailsResponse>,
    bestSalesAndNearbyStores: SnapshotStateList<BestSalesAndNearbyStores>,
    loadNextItems: (Int) -> Unit,
    isNearbyLoading: Boolean = false,
    success: Boolean = false,
    nearbyEndReached: Boolean = false,
    onFavoriteClick: (productId: Int, isFavorite: Boolean) -> Unit,
    onCategoryClick: (categoryId: Int) -> Unit,
    paginationMeta: PaginationMeta?
) {
    val displayedStoreKeys = mutableSetOf<Any>()
    val displayedBestSalesSet = mutableSetOf<BestSale>()

    val listState = rememberLazyListState()

    LazyColumn(
        modifier = Modifier
            .zIndex(1f)
            .fillMaxSize()
            .imePadding()
            .padding(padding),
        state = listState,
        content = {
            if (!homeBanners.isNullOrEmpty()) {
                item {
                    BannerWithAutomaticImageSwitching(homeBanners)
                }
                item {
                    SpacerHeightLarge()
                    SpacerHeightSmall()
                }
            }
            item {
                CategoriesSection(
                    label = stringResource(R.string.categories),
                    list = categories,
                    onCategoryClick = onCategoryClick,
                    onViewAllClick = {},
                )
            }
            item {
                SpacerHeightMedium()
            }
            items(
                count = bestSalesAndNearbyStores.size,
                key = {
                    bestSalesAndNearbyStores[it].hashCode()
                },
                itemContent = {
                    // Filter out and display only the non-duplicate BestDealsSection based on the bestSalesList
                    val uniqueBestSalesList =
                        bestSalesAndNearbyStores[it].bestSales.filter { bestSale ->
                            displayedBestSalesSet.add(bestSale)
                        }
                    if (bestSalesAndNearbyStores.isNotEmpty()
                        && bestSalesAndNearbyStores[it].nearbyStores.isNotEmpty()
                        && bestSalesAndNearbyStores[it].bestSales.isNotEmpty()
                    ) {
                        BestDealsSection(
                            modifier = Modifier,
                            list = uniqueBestSalesList
                        )
                        NearbyStoresHeader()
                        SpacerHeightSmall()
                        bestSalesAndNearbyStores[it].nearbyStores.filter { storeFilter ->
                            displayedStoreKeys.add(storeFilter.id)
                        }.forEachIndexed { index, store ->
                                StoreWithProductsComponent(
                                    modifier = Modifier,
                                    store = store,
                                    onFavoriteClick = { id, isFavorite ->
                                        onFavoriteClick(id, isFavorite)
                                    }
                                )
                        }
                    } else {
                        NotSupportedLocation()
                    }
                }
            )
            if (isNearbyLoading) {
                item {
                    PaginationLoader()
                }
            }
            item {
                SpacerHeightExtraLarge()
                SpacerHeightMedium()
                SpacerHeightMedium()
                SpacerHeightMedium()
            }
        }
    )


    LaunchedEffect(listState.layoutInfo) {
        val totalItems = listState.layoutInfo.totalItemsCount
        val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index

        if (
            lastVisibleItem != null
            && paginationMeta?.nextPage != null
            && !isNearbyLoading
            && !nearbyEndReached
            && lastVisibleItem >= totalItems - 1
        ) {
            loadNextItems(paginationMeta.nextPage)
        }
    }
}

 */