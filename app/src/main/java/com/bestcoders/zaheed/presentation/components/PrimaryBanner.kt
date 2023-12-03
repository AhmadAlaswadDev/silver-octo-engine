package com.bestcoders.zaheed.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.domain.model.products.HomeBanner
import com.bestcoders.zaheed.ui.theme.AppTheme
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BannerWithAutomaticImageSwitching(homeBanners: () -> List<HomeBanner>) {
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

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = AppTheme.dimens.paddingHorizontal)
            .clip(shape = RoundedCornerShape(Constants.CORNER_RADUIES)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .height(AppTheme.dimens.bannerHeight)
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(Constants.CORNER_RADUIES)),
        ) { page ->
            val banner = homeBanners()[page]
            AsyncImage(
                model = banner.photo,
                contentDescription = stringResource(R.string.banner_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        PagerDots(
            modifier = Modifier.wrapContentSize(),
            pagerState = pagerState,
            pageCount = homeBanners().size,
            indicatorWidth = 50,
        )
    }
}
