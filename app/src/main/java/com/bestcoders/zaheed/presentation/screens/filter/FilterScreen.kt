package com.bestcoders.zaheed.presentation.screens.filter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.core.util.UiEvent
import com.bestcoders.zaheed.core.util.header
import com.bestcoders.zaheed.navigation.Filter
import com.bestcoders.zaheed.presentation.components.NavigationTopBar
import com.bestcoders.zaheed.presentation.components.PrimarySnackbar
import com.bestcoders.zaheed.presentation.components.SpacerHeightLarge
import com.bestcoders.zaheed.presentation.components.SpacerHeightMedium
import com.bestcoders.zaheed.presentation.screens.filter.components.FilterComponent
import com.bestcoders.zaheed.presentation.screens.filter.components.FilterFooter
import com.bestcoders.zaheed.presentation.screens.filter.components.PriceRangeSlider
import com.bestcoders.zaheed.ui.theme.AppTheme
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun FilterScreen(
    state: MutableState<FilterState>,
    onEvent: (FilterEvent) -> Unit,
    onNavigateBackWithFilter: (Filter) -> Unit,
    clearFilter: () -> Unit,
    onNavigateBack: () -> Unit,
    resetState: (FilterState) -> Unit,
    uiEvent: SharedFlow<UiEvent>,
    showSortByFilter: MutableState<Boolean>,
) {

    val snackbarHostState = remember { SnackbarHostState() }
    val amountOfDiscountSelectedItem = remember { mutableStateOf(0) }
    val sortBySelectedItem = remember { mutableStateOf(0) }

    val priceRange =
        remember { mutableStateOf(Constants.settings.layoutsFiltersDefaultMinPrice.toFloat()..Constants.settings.layoutsFiltersDefaultMaxPrice.toFloat()) }

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

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            NavigationTopBar(
                label = stringResource(R.string.filter),
                onBackClicked = {
                    onNavigateBack()
                },
            )
        },
        content = { padding ->
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = AppTheme.dimens.paddingHorizontal,
                        end = AppTheme.dimens.paddingHorizontal,
                        top = padding.calculateTopPadding(),
                        bottom = padding.calculateBottomPadding()
                    ),
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.SpaceBetween,
                content = {
                    // Amount of discount list section
                    header {
                        Text(
                            text = stringResource(R.string.amount_of_discount),
                            style = MaterialTheme.typography.headlineSmall.copy(
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Start
                            ),
                        )
                    }
                    Constants.settings.layoutsFilters.amountOfDiscount.forEachIndexed { index, s ->
                        item {
                            FilterComponent(
                                index = index,
                                selectedItem = amountOfDiscountSelectedItem,
                                isAmountOfDiscount = true,
                                list = Constants.settings.layoutsFilters.amountOfDiscount,
                                onClick = {
                                    resetState(state.value.copy(amountOfDiscount = it))
                                }
                            )
                        }
                    }
                    if (showSortByFilter.value) {
                        header {
                            SpacerHeightMedium()
                        }
                        // Sort by list section
                        header {
                            Text(
                                text = stringResource(R.string.sort_by),
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.SemiBold,
                                    textAlign = TextAlign.Start
                                ),
                            )
                        }
                        Constants.settings.layoutsFilters.sortBy.forEachIndexed { index, s ->
                            item {
                                FilterComponent(
                                    index = index,
                                    list = Constants.settings.layoutsFilters.sortBy,
                                    selectedItem = sortBySelectedItem,
                                    onClick = {
                                        resetState(state.value.copy(sortBy = it))
                                    },
                                )
                            }
                        }
                    }
                    header {
                        SpacerHeightMedium()
                    }
                    // Price range slider
                    header {
                        PriceRangeSlider(
                            priceRange = priceRange,
                            minValue = Constants.settings.layoutsFiltersDefaultMinPrice.toInt(),
                            maxValue = Constants.settings.layoutsFiltersDefaultMaxPrice.toInt(),
                            onPriceRangeChanged = { min, max ->
                                // Handle the min and max price changes here
                                resetState(
                                    state.value.copy(
                                        priceRangeMin = min.toString(),
                                        priceRangeMax = max.toString()
                                    )
                                )
                            }
                        )
                    }
                    header {
                        SpacerHeightLarge()
                    }
                    // Reset Apply buttons
                    header {
                        FilterFooter(
                            onResetClick = {
                                amountOfDiscountSelectedItem.value = 0
                                sortBySelectedItem.value = 0
                                priceRange.value =
                                    Constants.settings.layoutsFiltersDefaultMinPrice.toFloat()..Constants.settings.layoutsFiltersDefaultMaxPrice.toFloat()
                                clearFilter()
                            },
                            onApplyClick = {
                                onNavigateBackWithFilter(
                                    Filter(
                                        amountOfDiscount = state.value.amountOfDiscount,
                                        priceRangeMax = state.value.priceRangeMax,
                                        priceRangeMin = state.value.priceRangeMin,
                                        sortBy = state.value.sortBy,
                                    )
                                )
                            },
                        )
                    }
                    header {
                        SpacerHeightLarge()
                    }
                }
            )
        }
    )
    PrimarySnackbar(snackbarHostState)
}
