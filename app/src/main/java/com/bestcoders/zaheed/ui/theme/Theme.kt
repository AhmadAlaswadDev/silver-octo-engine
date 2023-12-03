package com.bestcoders.zaheed.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val ColorPalette = lightColorScheme(
    primary = PrimaryColor,
    onPrimary = OnPrimaryColor,
    primaryContainer = PrimaryContainerColor,
    secondary = SecondaryColor,
    onSecondary = OnSecondaryColor,
    secondaryContainer = SecondaryContainerColor,
    tertiary = TertiaryColor,
    onTertiary = OnTertiaryColor,
    tertiaryContainer = TertiaryContainerColor,
    error = ErrorColor,
    errorContainer = ErrorContainerColor,
    background = BackgroundColor,
    surface = SurfaceColor,
)

@Composable
fun ZaheedTheme(
    windowSizeClass: WindowSizeClass,
    content: @Composable () -> Unit
) {

    val orientation = when {
        windowSizeClass.width.size > windowSizeClass.height.size -> Orientation.Landscape
        else -> Orientation.Portrait
    }

    val sizeThatMatters = when (orientation) {
        Orientation.Portrait -> windowSizeClass.width
        else -> windowSizeClass.height
    }

    val dimensions = when (sizeThatMatters) {
        is WindowSize.Small -> smallDimensions
        is WindowSize.Compact -> compactDimensions
        is WindowSize.Medium -> mediumDimensions
        else -> largeDimensions
    }

    val typography = when (sizeThatMatters) {
        is WindowSize.Small -> typographySmall
        is WindowSize.Compact -> typographyCompact
        is WindowSize.Medium -> typographyMedium
        else -> typographyBig
    }

    ProvideAppUtils(dimensions = dimensions, orientation = orientation) {
        MaterialTheme(
            colorScheme = ColorPalette,
            typography = typography,
            shapes = Shapes(),
            content = content
        )
    }


    // Optional, this part helps you set the statusbar color
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = ColorPalette.background.toArgb()

            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }
}

object AppTheme {
    val dimens: Dimensions
        @Composable get() = LocalAppDimens.current

    val orientation: Orientation
        @Composable get() = LocalOrientationMode.current
}