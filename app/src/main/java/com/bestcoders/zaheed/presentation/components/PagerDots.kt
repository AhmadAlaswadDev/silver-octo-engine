package com.bestcoders.zaheed.presentation.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bestcoders.zaheed.ui.theme.AppTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerDots(
    pagerState: androidx.compose.foundation.pager.PagerState,
    modifier: Modifier = Modifier,
    activeDotColor: Color = MaterialTheme.colorScheme.onTertiary,
    inactiveDotColor: Color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.2f),
    dotSize: Int = 12,
    spacing: Int = 8,
    indicatorWidth: Int = 20,
    pageCount:Int,
) {
    val currentPage by remember{ derivedStateOf { pagerState.currentPage } }

    Row(
        modifier = modifier.padding(bottom = AppTheme.dimens.medium),
        horizontalArrangement = Arrangement.spacedBy(spacing.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 0 until pageCount) {
            val dotWidth = if (i == currentPage) indicatorWidth.dp else dotSize.dp
            val dotSizeState by animateDpAsState(
                targetValue = dotWidth,
                animationSpec = tween(durationMillis = 200), label = ""
            )

            Box(
                modifier = Modifier
                    .height(10.dp)
                    .width(dotSizeState)
                    .clip(CircleShape)
                    .background(if (i == currentPage) activeDotColor else inactiveDotColor)
            )
        }
    }
}