package com.bestcoders.zaheed.presentation.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.ui.theme.CustomColor

@Composable
fun PrimaryProgress(modifier: Modifier = Modifier) {
    val scale = remember { Animatable(1.0f) }

    val infiniteRepeatableScaleOut = rememberInfiniteTransition(label = "")
    val scaleOutAnimationSpec = infiniteRepeatableScaleOut.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 500, easing = FastOutSlowInEasing)
        ), label = ""
    )

    val infiniteRepeatableScaleIn = rememberInfiniteTransition(label = "")
    val scaleInAnimationSpec = infiniteRepeatableScaleIn.animateFloat(
        initialValue = 1.3f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 500, easing = FastOutSlowInEasing)
        ), label = ""
    )

    // Start the animations
    LaunchedEffect(Unit) {
        while (true) {
            scale.animateTo(scaleOutAnimationSpec.value)
            scale.animateTo(scaleInAnimationSpec.value)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .zIndex(20f)
            .background(Color.Black.copy(alpha = 0.05f)),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(30.dp))
                .width(160.dp)
                .height(160.dp)
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(30.dp),
                    ambientColor = Color.Black.copy(alpha = 0.4f),
                    spotColor = Color.Black.copy(alpha = 0.3f),
                )
                .background(CustomColor.White),
            contentAlignment = Alignment.Center,
            content = {
                Image(
                    modifier = Modifier
                        .scale(scale.value)
                        .width(70.dp)
                        .height(70.dp),
                    painter = painterResource(id = R.drawable.logo_shape_icon),
                    contentDescription = null
                )
            }
        )
    }

}
