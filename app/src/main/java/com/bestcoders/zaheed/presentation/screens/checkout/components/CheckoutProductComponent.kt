package com.bestcoders.zaheed.presentation.screens.checkout.components

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.extentions.removeZerosAfterComma
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.domain.model.products.Product
import com.bestcoders.zaheed.presentation.components.LineDivider
import com.bestcoders.zaheed.presentation.components.MainPrice
import com.bestcoders.zaheed.presentation.components.SpacerWidthMediumLarge
import com.bestcoders.zaheed.presentation.components.SpacerWidthSmall
import com.bestcoders.zaheed.presentation.components.StrokedPrice
import com.bestcoders.zaheed.presentation.screens.checkout.CheckoutState
import com.bestcoders.zaheed.presentation.screens.home.components.DiscountBadge
import com.bestcoders.zaheed.ui.theme.AppTheme

@Composable
fun CheckoutProductComponent(
    product: Product,
    changeProductQuantity: (id: String, variant: String, quantity: Int, productPrice: Double) -> Unit,
    state: MutableState<CheckoutState>,
    removeProduct: () -> Unit,
) {

    val quantity = remember {
        mutableStateOf(product.quantity)
    }

    val isIncreaseClicked = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = state.value.changeProductQuantitySuccess, key2 = state.value.error) {
        if (!state.value.changeProductQuantitySuccess && !state.value.error.isNullOrEmpty() && !state.value.isLoading) {
            quantity.value = product.quantity
            state.value = state.value.copy(error = null, changeProductQuantitySuccess = false)
        } else if (state.value.changeProductQuantitySuccess && state.value.error.isNullOrEmpty() && !state.value.isLoading) {
            product.quantity = quantity.value
            if (isIncreaseClicked.value) {
                increaseCartTotalPrice(
                    productPrice = product.mainPrice.toDouble(),
                    state = state
                )
            } else {
                decreaseCartTotalPrice(
                    productPrice = product.mainPrice.toDouble(),
                    state = state
                )
            }
            state.value = state.value.copy(error = null, changeProductQuantitySuccess = false)
        }
    }



    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        content = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                content = {
                    Card(
                        modifier = Modifier
                            .size(AppTheme.dimens.productImageCheckoutSize),
                        shape = RoundedCornerShape(Constants.CORNER_RADUIES.dp),
                        border = BorderStroke(
                            2.dp,
                            MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f)
                        ),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(
                                alpha = 0.1f
                            ),
                            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer.copy(
                                alpha = 0.1f
                            ),
                        ),
                        content = {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                content = {
                                    AsyncImage(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .zIndex(1f),
                                        model = product.thumbnailImage,
                                        contentScale = ContentScale.Crop,
                                        contentDescription = null
                                    )
                                    DiscountBadge(
                                        modifier = Modifier
                                            .offset(x = 8.dp, y = 8.dp)
                                            .height(AppTheme.dimens.discountBadgeHeight)
                                            .wrapContentWidth()
                                            .zIndex(3f),
                                        label = product.discount.toString()
                                    )
                                },
                            )
                        },
                    )
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Top,
                        content = {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 5.dp),
                                text = product.name,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    textAlign = TextAlign.Start,
                                    color = MaterialTheme.colorScheme.primary,
                                ),
                            )
                            Log.e("ASFUAJKS", "CheckoutProductComponent: ${product.cartColor}", )
                            val productChoices = StringBuffer()
                            if (product.cartColor != null) {
                                productChoices.append(stringResource(R.string.color_with) + product.cartColor.name + "\n")
                            }
                            if (product.cartChoices!!.isNotEmpty()) {
                                product.cartChoices.forEach {
                                    productChoices.append(
                                        it.name + ": " + it.cartValue?.name + "\n"
                                    )
                                }
                                productChoices.append(stringResource(R.string.the_quantity) + ": " + quantity.value.toString())
                            }
                            if(!productChoices.isNullOrEmpty()){
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 5.dp),
                                    text = productChoices.toString(),
                                    overflow = TextOverflow.Clip,
                                    lineHeight = 18.sp,
                                    minLines = 1,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontWeight = FontWeight.SemiBold,
                                        textAlign = TextAlign.Start,
                                        color = MaterialTheme.colorScheme.secondary.copy(
                                            alpha = 0.5f
                                        ),
                                    ),
                                )
                            }
                        },
                    )
                },
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                content = {
                    Row(
                        modifier = Modifier
                            .width(AppTheme.dimens.productImageCheckoutSize)
                            .wrapContentHeight(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        content = {
                            IconButton(
                                modifier = Modifier.size(AppTheme.dimens.productQuantityCheckoutIconSize),
                                onClick = {
                                    isIncreaseClicked.value = false
                                    if (quantity.value == 1) {
                                        removeProduct()
                                    } else if (quantity.value!! > 1) {
                                        quantity.value = quantity.value!! - 1
                                        changeProductQuantity(
                                            product.id.toString(),
                                            product.variant!!,
                                            quantity.value!!,
                                            product.mainPrice.toDouble(),
                                        )

                                    }
                                },
                                content = {
                                    Icon(
                                        modifier = Modifier.fillMaxSize(),
                                        painter = painterResource(id = R.drawable.minus_icon),
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onPrimary
                                    )
                                },
                            )
                            Text(
                                text = quantity.value.toString(),
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colorScheme.primary
                                ),
                            )
                            IconButton(
                                modifier = Modifier.size(AppTheme.dimens.productQuantityCheckoutIconSize),
                                onClick = {
                                    isIncreaseClicked.value = true
                                    quantity.value = quantity.value!! + 1

                                    changeProductQuantity(
                                        product.id.toString(),
                                        product.variant!!,
                                        quantity.value!!,
                                        product.mainPrice.toDouble(),
                                    )

                                },
                                content = {
                                    Icon(
                                        modifier = Modifier.fillMaxSize(),
                                        painter = painterResource(id = R.drawable.plus_icon),
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onPrimary
                                    )
                                },
                            )
                        },
                    )
                    Column(
                        modifier = Modifier,
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        content = {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.End,
                                content = {
                                    StrokedPrice(
                                        price = product.strokedPrice.toString()
                                            .removeZerosAfterComma()
                                    )
                                    SpacerWidthMediumLarge()
                                    MainPrice(
                                        price = product.mainPrice.toString(),
                                        textStyle = MaterialTheme.typography.headlineSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            textAlign = TextAlign.Start,
                                            color = MaterialTheme.colorScheme.onPrimary,
                                        )
                                    )
                                },
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.End,
                                content = {
                                    Text(
                                        text = stringResource(id = R.string.you_saved),
                                        maxLines = 1,
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            fontWeight = FontWeight.Normal,
                                            textAlign = TextAlign.Start,
                                            color = MaterialTheme.colorScheme.primary,
                                        ),
                                    )
                                    SpacerWidthSmall()
                                    MainPrice(
                                        price = product.saved.toString(),
                                        textStyle = MaterialTheme.typography.bodySmall.copy(
                                            fontWeight = FontWeight.SemiBold,
                                            textAlign = TextAlign.Start,
                                            color = MaterialTheme.colorScheme.onSecondary,
                                        )
                                    )
                                },
                            )
                        },
                    )
                },
            )
            LineDivider()
        },
    )
}

fun increaseCartTotalPrice(productPrice: Double, state: MutableState<CheckoutState>) {
    state.value.cartByStoreModel!!.summary.cartTotal =
        state.value.cartByStoreModel!!.summary.cartTotal + productPrice
}

fun decreaseCartTotalPrice(productPrice: Double, state: MutableState<CheckoutState>) {
    state.value.cartByStoreModel!!.summary.cartTotal =
        state.value.cartByStoreModel!!.summary.cartTotal - productPrice
}
