package com.bestcoders.zaheed.presentation.screens.product_details.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.bestcoders.zaheed.presentation.components.PagerDots
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductDetailsBanner(
    productImages: () -> List<String>
) {

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = {productImages().size},
    )

    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            if (pagerState.currentPage < productImages().size - 1) {
                pagerState.animateScrollToPage(pagerState.currentPage + 1)
            } else {
                pagerState.animateScrollToPage(0)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            val banner = productImages()[page]
            val painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current).data(data = banner)
                    .apply(block = fun ImageRequest.Builder.() {
                        crossfade(true)
                    }).build()
            )
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
        }
        PagerDots(
            modifier = Modifier.wrapContentSize(),
            pagerState = pagerState,
            pageCount = productImages().size,
            indicatorWidth = 50,
        )
    }
}

