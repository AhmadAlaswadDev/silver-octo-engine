package com.bestcoders.zaheed.presentation.screens.product_details

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.extentions.removeZerosAfterComma
import com.bestcoders.zaheed.core.util.UiEvent
import com.bestcoders.zaheed.domain.model.products.Choice
import com.bestcoders.zaheed.domain.model.products.ColorModel
import com.bestcoders.zaheed.presentation.components.PrimaryProgress
import com.bestcoders.zaheed.presentation.components.PrimarySnackbar
import com.bestcoders.zaheed.presentation.screens.product_details.components.OfferTime
import com.bestcoders.zaheed.presentation.screens.product_details.components.ProductChoiceSelector
import com.bestcoders.zaheed.presentation.screens.product_details.components.ProductColors
import com.bestcoders.zaheed.presentation.screens.product_details.components.ProductDetailsFooter
import com.bestcoders.zaheed.presentation.screens.product_details.components.ProductDetailsHeader
import com.bestcoders.zaheed.presentation.screens.product_details.components.ProductDetailsTextSubtitle
import com.bestcoders.zaheed.presentation.screens.product_details.components.ProductDetailsTextTitle
import com.bestcoders.zaheed.presentation.screens.product_details.components.ProductNotAvailableFooter
import com.bestcoders.zaheed.presentation.screens.product_details.components.ProductPriceWithItemsCount
import com.bestcoders.zaheed.presentation.screens.product_details.components.StoreInfoProductDetails
import com.bestcoders.zaheed.presentation.screens.product_details.components.YouSaved
import com.bestcoders.zaheed.ui.theme.AppTheme
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest


@Composable
fun ProductDetailsScreen(
    state: MutableState<ProductDetailsState>,
    onEvent: (ProductDetailsEvent) -> Unit,
    resetState: (ProductDetailsState) -> Unit,
    uiEvent: SharedFlow<UiEvent>,
    navigateToStoreDetails: (storeId: Int) -> Unit,
    onBackClicked: () -> Unit,
) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val quantity = remember {
        mutableStateOf(1)
    }
    val choices by remember {
        derivedStateOf { mutableStateListOf<Choice>() }
    }
    val colors by remember {
        derivedStateOf { mutableListOf<ColorModel>() }
    }
    val selectedVariant = remember {
        mutableStateOf("")
    }
    val availableVariations = remember {
        mutableStateOf(mutableListOf<String>())
    }
    val selectedItem = remember { mutableStateOf(0) }
    var isAvailableVariantProp by remember {
        mutableStateOf(false)
    }

    fun isAvailableVariant(variant: String) {
        val result = availableVariations.value.contains(variant)
        isAvailableVariantProp = result
        if (result) {
            onEvent(
                ProductDetailsEvent.GetProductVariationDetails(
                    variant = variant,
                    productId = state.value.productDetails!!.id
                )
            )
        }
    }




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

    LaunchedEffect(state.value.productDetails) {
        if (state.value.productDetails != null) {
            choices.addAll(state.value.productDetails!!.choices)
            colors.addAll(state.value.productDetails!!.colors)
            selectedVariant.value = state.value.productDetails!!.selectedVariant
            availableVariations.value =
                state.value.productDetails!!.availableVariations.toMutableStateList()
            isAvailableVariant(selectedVariant.value)
        }
    }

    LaunchedEffect(state.value.addProductToCartSuccess) {
        if (state.value.addProductToCartSuccess) {
            Toast.makeText(
                context,
                context.getString(R.string.product_added_to_cart),
                Toast.LENGTH_SHORT
            ).show()
            onEvent(ProductDetailsEvent.ShowSnackBar(message = context.getString(R.string.product_added_to_cart)))
            resetState(state.value.copy(addProductToCartSuccess = false))
            onBackClicked()
            return@LaunchedEffect
        }
    }

    LaunchedEffect(state.value.error) {
        if (!state.value.error.isNullOrEmpty()) {
            Toast.makeText(context, state.value.error.toString(), Toast.LENGTH_SHORT).show()
            onEvent(ProductDetailsEvent.ShowSnackBar(message = state.value.error.toString()))
            resetState(state.value.copy(error = null))
            return@LaunchedEffect
        }
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (state.value.productDetails != null) {
                if (isAvailableVariantProp) {
                    ProductDetailsFooter(
                        productPrice = (state.value.productVariationDetails?.mainPrice
                            ?: state.value.productDetails!!.mainPrice),
                        quantity = quantity,
                        onAddToCartButtonClick = {
                            onEvent(
                                ProductDetailsEvent.AddProductToCart(
                                    productId = state.value.productDetails!!.id,
                                    variant = selectedVariant.value,
                                    quantity = quantity.value
                                )
                            )
                        }
                    )
                } else {
                    ProductNotAvailableFooter()
                }
            }
        },
        content = { padding ->
            if (state.value.productDetails != null) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    if (state.value.productDetails != null) {
                        item {
                            ProductDetailsHeader(
                                product = state.value.productDetails!!,
                                productVariationDetails =  state.value.productVariationDetails,
                                onBackClicked = onBackClicked,
                                onFavoriteClick = {
                                    onEvent(
                                        ProductDetailsEvent.OnFavoriteProductClick(
                                            state.value.productDetails!!.id,
                                            state.value.productDetails!!.isFavorite
                                        )
                                    )
                                },
                            )
                        }
                        item {
                            StoreInfoProductDetails(
                                store = state.value.productDetails!!.shop,
                                onStoreClick = navigateToStoreDetails
                            )
                        }
                        item {
                            ProductDetailsTextTitle(text = state.value.productDetails!!.name)
                        }
                        item {
                            ProductDetailsTextSubtitle(text = state.value.productDetails!!.category.name)
                        }
                        item {
                            val spannedText = HtmlCompat.fromHtml(state.value.productDetails!!.description, 0)
                            AndroidView(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = AppTheme.dimens.paddingHorizontal),
                                factory = { MaterialTextView(it) },
                                update = { it.text = spannedText }
                            )
                        }
                        item {
                            YouSaved(
                                saved = state.value.productVariationDetails?.saved
                                    ?: state.value.productDetails!!.saved
                            )
                        }
                        item {
                            ProductPriceWithItemsCount(
                                isAvailableVariantProp = isAvailableVariantProp,
                                quantity = quantity,
                                strokedPrice = (state.value.productVariationDetails?.strokedPrice
                                    ?: state.value.productDetails!!.strokedPrice).toString()
                                    .removeZerosAfterComma(),
                                mainPrice = (state.value.productVariationDetails?.mainPrice
                                    ?: state.value.productDetails!!.mainPrice).toString()
                                    .removeZerosAfterComma(),
                            )
                        }
                        item {
                            OfferTime(state.value.productDetails!!)
                        }
                        if (colors.isNotEmpty()) {
                            item {
                                ProductColors(
                                    colors = colors,
                                    selectedItem = selectedItem,
                                    selectedVariant = selectedVariant,
                                    availableVariations = availableVariations.value,
                                    onClick = { newVariant ->
                                        selectedVariant.value = newVariant
                                        isAvailableVariant(newVariant)
                                    },
                                )
                            }
                        }
                        items(
                            count = choices.size,
                            key = {
                                choices[it].id
                            },
                            itemContent = { index ->
                                ProductChoiceSelector(
                                    subChoices = choices[index].choices,
                                    name = choices[index].name,
                                    selectedVariant = selectedVariant,
                                    availableVariations = availableVariations.value,
                                    colors = colors,
                                    index1 = index,
                                    onChoiceSelected = { newVariant ->
                                        selectedVariant.value = newVariant
                                        isAvailableVariant(newVariant)
                                    }
                                )
                            },
                        )
                    }
                }
            } else {
                PrimaryProgress()
            }
        }
    )
    PrimarySnackbar(snackbarHostState)
}



