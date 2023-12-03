package com.bestcoders.zaheed.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bestcoders.zaheed.ui.theme.AppTheme

@Composable
fun SpacerHeightMedium() = Spacer(modifier = Modifier.height(AppTheme.dimens.medium))

@Composable
fun SpacerHeightMediumLarge() = Spacer(modifier = Modifier.height(AppTheme.dimens.mediumLarge))

@Composable
fun SpacerHeightSmall() = Spacer(modifier = Modifier.height(AppTheme.dimens.small))

@Composable
fun SpacerHeightLarge() = Spacer(modifier = Modifier.height(AppTheme.dimens.large))

@Composable
fun SpacerHeightExtraLarge() = Spacer(modifier = Modifier.height(AppTheme.dimens.extraLarge))

@Composable
fun SpacerWidthMedium() = Spacer(modifier = Modifier.width(AppTheme.dimens.medium))

@Composable
fun SpacerWidthMediumLarge() = Spacer(modifier = Modifier.width(AppTheme.dimens.mediumLarge))

@Composable
fun SpacerWidthSmall() = Spacer(modifier = Modifier.width(AppTheme.dimens.small))

@Composable
fun SpacerWidthLarge() = Spacer(modifier = Modifier.width(AppTheme.dimens.large))
