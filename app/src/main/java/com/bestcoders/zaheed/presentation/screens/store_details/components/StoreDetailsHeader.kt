package com.bestcoders.zaheed.presentation.screens.store_details.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.extentions.removePadding
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.domain.model.products.StoreDetails
import com.bestcoders.zaheed.presentation.components.PrimaryButton
import com.bestcoders.zaheed.presentation.screens.store_details.StoreDetailsEvent
import com.bestcoders.zaheed.ui.theme.AppTheme
import com.bestcoders.zaheed.ui.theme.CustomColor
import kotlinx.coroutines.delay

@Composable
fun StoreDetailsHeader(
    onBackClicked: () -> Unit,
    onEvent: (StoreDetailsEvent) -> Unit,
    onLocationIconClick: () -> Unit,
    storeDetails: StoreDetails,
) {
    Box(
        modifier = Modifier
            .removePadding(-AppTheme.dimens.paddingHorizontal)
            .fillMaxWidth()
            .wrapContentHeight(),
        contentAlignment = Alignment.TopCenter,
        content = {
            // Store Image
            HeaderImageStoreDetails(
                sliders = storeDetails!!.sliders,
                store = storeDetails,
                onBackClicked = onBackClicked,
                onEvent = onEvent
            )
            // Store Info
            StoreInfoStoreDetailsHeader(
                store = storeDetails,
                onEvent = onEvent,
                onLocationIconClick = onLocationIconClick
            )
        },
    )
}

@Composable
fun HeaderImageStoreDetails(
    onBackClicked: () -> Unit,
    onEvent: (StoreDetailsEvent) -> Unit,
    sliders: List<String>,
    store: StoreDetails,
) {

    val isFavorite = rememberSaveable {
        mutableStateOf(store.isFavorite)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(AppTheme.dimens.headerStoreDetailsHeight),
        content = {

            StoreDetailsSlider { sliders }
            Box(
                modifier = Modifier
                    .zIndex(2f)
                    .padding(AppTheme.dimens.paddingHorizontal)
                    .fillMaxSize(),
                content = {
                    Box(
                        modifier = Modifier
                            .size(AppTheme.dimens.topBarBoxIconStoreDetailsSize)
                            .clickable { onBackClicked() }
                            .align(Alignment.TopStart)
                            .background(
                                Color.Black.copy(alpha = 0.15f),
                                shape = RoundedCornerShape(Constants.CORNER_RADUIES.dp)
                            ),
                        contentAlignment = Alignment.Center,
                        content = {
                            Icon(
                                modifier = Modifier
                                    .size(AppTheme.dimens.topBarIconStoreDetailsSize)
                                    .graphicsLayer(
                                        rotationY = when (Constants.DEFAULT_LANGUAGE) {
                                            Constants.SAUDI_LANGUAGE_CODE -> 180f
                                            else -> 0f
                                        }
                                    ),
                                tint = CustomColor.White,
                                imageVector = Icons.Rounded.ArrowBack,
                                contentDescription = null
                            )
                        }
                    )
                    Box(
                        modifier = Modifier
                            .size(AppTheme.dimens.topBarBoxIconStoreDetailsSize)
                            .clickable {
                                isFavorite.value = !isFavorite.value
                                onEvent(
                                    StoreDetailsEvent.OnFavoriteStoreClick(
                                        storeId = store.id,
                                        isFavorite = store.isFavorite
                                    )
                                )
                                store.isFavorite = !store.isFavorite
                            }
                            .align(Alignment.TopEnd)
                            .background(
                                Color.Black.copy(alpha = 0.15f),
                                shape = RoundedCornerShape(Constants.CORNER_RADUIES.dp)
                            ),
                        contentAlignment = Alignment.Center,
                        content = {
                            Icon(
                                modifier = Modifier.size(AppTheme.dimens.topBarIconStoreDetailsSize),
                                painter = if (isFavorite.value) {
                                    painterResource(id = R.drawable.favorite_selected_icon)
                                } else {
                                    painterResource(id = R.drawable.favorite_unselected_icon)
                                },
                                tint = if (isFavorite.value) {
                                    MaterialTheme.colorScheme.onPrimary
                                } else {
                                    CustomColor.White
                                },
                                contentDescription = stringResource(id = R.string.favorite_not_selcted_icon)
                            )
                        }
                    )
                },
            )
        },
    )
}


@Composable
fun StoreInfoStoreDetailsHeader(
    store: StoreDetails,
    onEvent: (StoreDetailsEvent) -> Unit,
    onLocationIconClick: () -> Unit,
) {

    val isSubscribed = rememberSaveable {
        mutableStateOf(store.isSubscribed)
    }

    Column(
        modifier = Modifier
            .background(Color.Transparent)
            .padding(horizontal = AppTheme.dimens.paddingHorizontal)
            .fillMaxWidth()
            .height(AppTheme.dimens.storeInfoStoreDetailsHeight)
            .offset(y = AppTheme.dimens.storeInfoStoreDetailsOffset),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp),
        content = {
            AsyncImage(
                modifier = Modifier
                    .background(
                        color = CustomColor.White,
                        shape = RoundedCornerShape(Constants.CORNER_RADUIES)
                    )
                    .size(AppTheme.dimens.storeImageStoreDetailsSize)
                    .clip(RoundedCornerShape(Constants.CORNER_RADUIES))
                    .align(Alignment.CenterHorizontally)
                    .border(
                        border = BorderStroke(
                            width = 5.dp,
                            color = CustomColor.White
                        ),
                        shape = RoundedCornerShape(Constants.CORNER_RADUIES)
                    ),
                model = store.logo,
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = store.name,
                overflow = TextOverflow.Clip,
                maxLines = 1,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary
                ),
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "(" + store.branchName + ")",
                overflow = TextOverflow.Clip,
                lineHeight = 25.sp,
                maxLines = 2,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary
                ),
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = store.category.name,
                overflow = TextOverflow.Clip,
                maxLines = 1,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.secondary.copy(
                        alpha = 0.5f
                    )
                ),
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                content = {
                    Text(
                        text = store.address + " (" + store.distance + stringResource(id = R.string.km) + ")",
                        overflow = TextOverflow.Clip,
                        maxLines = 1,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.primary
                        ),
                    )
                    Icon(
                        modifier = Modifier
                            .size(AppTheme.dimens.mapTrifoldStoreDetailsSize)
                            .clickable {
                                onLocationIconClick()
                            },
                        painter = painterResource(id = R.drawable.map_trifold),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                },
            )


            if (store.isSubscribed) {
                PrimaryButton(
                    modifier = Modifier
                        .width(AppTheme.dimens.subscribeButtonStoreDetailsWidth)
                        .height(AppTheme.dimens.subscribeButtonStoreDetailsHeight),
                    text = stringResource(R.string.subscribed),
                    onClick = {
                        if (store.isSubscribed) {
                            isSubscribed.value = !isSubscribed.value
                            onEvent(StoreDetailsEvent.UnFollowStore(storeId = store.id))
                            store.isSubscribed = !store.isSubscribed
                        } else {
                            isSubscribed.value = !isSubscribed.value
                            onEvent(StoreDetailsEvent.FollowStore(storeId = store.id))
                            store.isSubscribed = !store.isSubscribed
                        }
                    },
                    color = CustomColor.White,
                    borderStroke = 1.5f,
                    borderColor = MaterialTheme.colorScheme.onTertiary,
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onTertiary,
                        fontWeight = FontWeight.SemiBold,
                    ),
                    iconSize = AppTheme.dimens.topBarIconStoreDetailsSize,
                    icon = R.drawable.success_icon,
                    iconColor = MaterialTheme.colorScheme.onTertiary
                )
            } else {
                PrimaryButton(
                    modifier = Modifier
                        .width(AppTheme.dimens.subscribeButtonStoreDetailsWidth)
                        .height(AppTheme.dimens.subscribeButtonStoreDetailsHeight),
                    text = stringResource(R.string.subscribe),
                    onClick = {
                        if (store.isSubscribed) {
                            isSubscribed.value = !isSubscribed.value
                            onEvent(StoreDetailsEvent.UnFollowStore(storeId = store.id))
                            store.isSubscribed = !store.isSubscribed
                        } else {
                            isSubscribed.value = !isSubscribed.value
                            onEvent(StoreDetailsEvent.FollowStore(storeId = store.id))
                            store.isSubscribed = !store.isSubscribed
                        }
                    },
                    color = MaterialTheme.colorScheme.onTertiary,
                    borderStroke = 1.5f,
                    borderColor = MaterialTheme.colorScheme.onTertiary,
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        color = CustomColor.White,
                        fontWeight = FontWeight.SemiBold,
                    ),
                )
            }
        },
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StoreDetailsSlider(modifier: Modifier = Modifier, homeBanners: () -> List<String>) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = {homeBanners().size},
    )

    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            if (pagerState.currentPage < homeBanners().size - 1) {
                pagerState.animateScrollToPage(pagerState.currentPage + 1)
            } else {
                pagerState.animateScrollToPage(0)
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(AppTheme.dimens.bannerHeight),
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            val image = homeBanners()[page]
            AsyncImage(
                model = image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}