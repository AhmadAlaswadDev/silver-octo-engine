package com.bestcoders.zaheed.presentation.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.zIndex
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.data.remote.model.products.PaginationMeta
import com.bestcoders.zaheed.domain.model.products.HomeLayout
import com.bestcoders.zaheed.presentation.components.BannerWithAutomaticImageSwitching
import com.bestcoders.zaheed.presentation.components.BestDealsSection
import com.bestcoders.zaheed.presentation.components.CategoriesSection
import com.bestcoders.zaheed.presentation.components.PaginationLoader
import com.bestcoders.zaheed.presentation.components.SpacerHeightLarge
import com.bestcoders.zaheed.presentation.components.StoreWithProductsComponent
import com.bestcoders.zaheed.ui.theme.AppTheme

@Composable
fun HomeContent(
    padding: PaddingValues,
    homePadding: PaddingValues,
    loadNextItems: (Int) -> Unit,
    isNearbyLoading: Boolean = false,
    nearbyEndReached: Boolean = false,
    onFavoriteClick: (productId: Int, isFavorite: Boolean) -> Unit,
    onCategoryClick: (categoryId: Int, categoryName: String) -> Unit,
    onProductClick: (Int) -> Unit,
    homeLayoutList: SnapshotStateList<HomeLayout>?,
    onStoreClick: (Int) -> Unit
) {

    val listState = rememberScrollState()
    val paginationMeta = remember {
        mutableStateOf<PaginationMeta?>(null)
    }

    LaunchedEffect(!listState.canScrollForward) {
        if (
            homeLayoutList!![homeLayoutList.lastIndex].paginationMeta != null
            && homeLayoutList[homeLayoutList.lastIndex].paginationMeta?.nextPage != null
            && !isNearbyLoading
            && !nearbyEndReached
            && !listState.canScrollForward
        ) {
            loadNextItems(homeLayoutList[homeLayoutList.lastIndex].paginationMeta!!.nextPage!!)
        }
    }

    Column(
        modifier = Modifier
            .zIndex(1f)
            .fillMaxSize()
            .imePadding()
            .verticalScroll(listState)
            .padding(
                top = padding.calculateTopPadding(),
                bottom = homePadding.calculateBottomPadding()
            ),
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.medium),
        content = {
            SpacerHeightLarge()
            if (!homeLayoutList.isNullOrEmpty()) {
                homeLayoutList.forEachIndexed { index, store ->
                    paginationMeta.value = homeLayoutList[index].paginationMeta
                    if (!homeLayoutList[index].homeBanner.isNullOrEmpty()) {
                        BannerWithAutomaticImageSwitching { homeLayoutList[index].homeBanner!! }
                    }
                    if (!homeLayoutList[index].categories.isNullOrEmpty()) {
                        CategoriesSection(
                            list = homeLayoutList[index].categories!!,
                            onCategoryClick = onCategoryClick,
                        )
                    }
                    if (!homeLayoutList[index].bestSales.isNullOrEmpty()) {
                        BestDealsSection(
                            modifier = Modifier,
                            list = homeLayoutList[index].bestSales!!,
                            onFavoriteClick = { id, isFavorite ->
                                onFavoriteClick(id, isFavorite)
                            },
                            onProductClick = onProductClick
                        )
                    }
                    if (!homeLayoutList[index].nearbyStores.isNullOrEmpty()) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = AppTheme.dimens.paddingHorizontal,
                                    vertical = AppTheme.dimens.medium,
                                ),
                            text = stringResource(R.string.nearby_stores),
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.SemiBold
                            ),
                        )
                        homeLayoutList[index].nearbyStores!!.forEachIndexed { index1, store ->
                            StoreWithProductsComponent(
                                store = store,
                                onFavoriteClick = { id, isFavorite ->
                                    onFavoriteClick(id, isFavorite)
                                },
                                onProductClick = onProductClick,
                                onStoreClick = onStoreClick,
                            )
                        }
                    }
                }
                if (isNearbyLoading && !nearbyEndReached) {
                    PaginationLoader()
                }
            }
        }
    )
}


