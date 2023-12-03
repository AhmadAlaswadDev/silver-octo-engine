package com.bestcoders.zaheed.presentation.screens.store_details

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.extentions.navigateToLocationInGoogleMaps
import com.bestcoders.zaheed.core.util.UiEvent
import com.bestcoders.zaheed.core.util.header
import com.bestcoders.zaheed.domain.model.products.Product
import com.bestcoders.zaheed.presentation.components.PrimaryProgress
import com.bestcoders.zaheed.presentation.components.PrimarySnackbar
import com.bestcoders.zaheed.presentation.components.ProductComponent
import com.bestcoders.zaheed.presentation.screens.store_details.components.CategorySelector
import com.bestcoders.zaheed.presentation.screens.store_details.components.MoreDetailsInfoStoreDetails
import com.bestcoders.zaheed.presentation.screens.store_details.components.StoreDetailsHeader
import com.bestcoders.zaheed.ui.theme.AppTheme
import com.bestcoders.zaheed.ui.theme.CustomColor
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StoreDetailsScreen(
    state: MutableState<StoreDetailsState>,
    onEvent: (StoreDetailsEvent) -> Unit,
    resetState: (StoreDetailsState) -> Unit,
    uiEvent: SharedFlow<UiEvent>,
    onBackClicked: () -> Unit,
    navigateToReturnPolicy: (storeReturnPolicy: String) -> Unit,
    navigateToCheckout: (storeId: Int) -> Unit,
    navigateToProductDetails: (productId: Int) -> Unit,
) {

    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val selectedItem = remember { mutableIntStateOf(0) }
    val sortedList = remember { mutableStateListOf<Product>() }
    val sections = remember {
        derivedStateOf { mutableStateListOf(context.getString(R.string.all)) }
    }
    val storeDetails = remember {
        derivedStateOf { state.value.storeDetails }
    }
    val listState = rememberLazyGridState()


    LaunchedEffect(true) {
        uiEvent.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message, actionLabel = event.action
                    )
                }
            }
        }
    }
    LaunchedEffect(state.value.storeDetails) {
        if (state.value.storeDetails != null) {
            sortedList.addAll(state.value.allProducts)
        }
    }
    LaunchedEffect(state.value.storeDetails) {
        if (state.value.storeDetails != null && state.value.storeDetails!!.sections.isNotEmpty()) {
            state.value.storeDetails!!.sections.forEach {
                sections.value.add(it.sectionName)
            }
        }
    }
    LaunchedEffect(state.value.error) {
        if (!state.value.error.isNullOrEmpty()) {
            onEvent(StoreDetailsEvent.ShowSnackBar(message = state.value.error.toString()))
            resetState(state.value.copy(error = null))
            return@LaunchedEffect
        }
    }
    LaunchedEffect(!listState.canScrollForward) {
        if (selectedItem.intValue == 0
            && state.value.paginationMeta != null
            && state.value.paginationMeta?.nextPage != null
            && !listState.canScrollForward
            && !state.value.isLoading
        ) {
            onEvent(
                StoreDetailsEvent.LoadNextData(
                    state.value.storeDetails!!.id,
                    state.value.paginationMeta!!.nextPage!!
                )
            )
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier
                    .size(AppTheme.dimens.floatingButtonScreenDetailsSize)
                    .background(color = MaterialTheme.colorScheme.onPrimary, shape = CircleShape),
                onClick = {
                    if (storeDetails.value != null) {
                        navigateToCheckout(storeDetails.value!!.id)
                    }
                },
                shape = CircleShape,
                content = {
                    Icon(
                        painter = painterResource(id = R.drawable.cart_unselected_icon),
                        contentDescription = null,
                        tint = CustomColor.White
                    )
                },
            )
        },
        content = { padding ->
            if (storeDetails.value != null && !state.value.isLoading) {
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            start = AppTheme.dimens.paddingHorizontal,
                            end = AppTheme.dimens.paddingHorizontal,
                            bottom = padding.calculateBottomPadding(),
                            top = padding.calculateTopPadding()
                        ),
                    state = listState,
                    verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.storeDetailsListVerticalArrangement),
                    columns = GridCells.Fixed(2),
                    content = {
                        // Header
                        if (storeDetails.value != null) {
                            header {
                                StoreDetailsHeader(
                                    storeDetails = storeDetails.value!!,
                                    onBackClicked = onBackClicked,
                                    onEvent = onEvent,
                                    onLocationIconClick = {
                                        navigateToLocationInGoogleMaps(
                                            context = context,
                                            locationQuery = storeDetails.value!!.address
                                        )
                                    }
                                )
                            }
                        }
                        // More Details
                        if (storeDetails.value != null) {
                            header {
                                MoreDetailsInfoStoreDetails(
                                    storeDetails = storeDetails.value!!,
                                    onReturnPolicyClick = navigateToReturnPolicy,
                                )
                            }
                        }
                        if (sections.value.isNotEmpty() && sortedList.isNotEmpty()) {
                            header {
                                CategorySelector(
                                    selectedItem = selectedItem,
                                    list = sections.value,
                                    productCount = sortedList.size,
                                    onClick = { index ->
                                        sortedList.clear()
                                        if (index == 0) {
                                            sortedList.addAll(state.value.allProducts)
                                        } else {
                                            sortedList.addAll(storeDetails.value!!.sections[selectedItem.intValue - 1].products)
                                        }
                                    },
                                )
                            }
                            items(
                                count = sortedList.size,
                                key = {
                                    sortedList[it].hashCode()
                                }
                            ) { index ->
                                val item = sortedList[index]
                                ProductComponent(
                                    modifier = Modifier.animateItemPlacement().padding(
                                        horizontal = AppTheme.dimens.small,
                                        vertical = AppTheme.dimens.medium
                                    ),
                                    product = item,
                                    onProductClick = navigateToProductDetails
                                )
                            }
                        }
                    },
                )
            } else {
                PrimaryProgress()
            }
        },
    )
    PrimarySnackbar(snackbarHostState)
}

